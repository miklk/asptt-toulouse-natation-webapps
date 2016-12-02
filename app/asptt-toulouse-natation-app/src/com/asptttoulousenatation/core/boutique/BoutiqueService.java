package com.asptttoulousenatation.core.boutique;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.boutique.OrderDao;
import com.asptttoulousenatation.core.server.dao.boutique.OrderProductDao;
import com.asptttoulousenatation.core.server.dao.boutique.ProductDao;
import com.asptttoulousenatation.core.server.dao.entity.boutique.OrderEntity;
import com.asptttoulousenatation.core.server.dao.entity.boutique.OrderProductEntity;
import com.asptttoulousenatation.core.server.dao.entity.boutique.ProductEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;

@Path("/boutique")
@Produces("application/json")
public class BoutiqueService {

	private static final Logger LOG = Logger.getLogger(BoutiqueService.class.getName());

	private ProductDao productDao = new ProductDao();
	private OrderDao orderDao = new OrderDao();
	private OrderProductDao orderProductDao = new OrderProductDao();
	private DossierDao dossierDao = new DossierDao();

	@Path("products")
	@GET
	public List<ProductEntity> products() {
		List<ProductEntity> products = productDao.getAll();
		return products;
	}

	@Path("order")
	@PUT
	public void order(BoutiqueOrderParameters parameters) {
		OrderEntity order = new OrderEntity();
		if (parameters.getDossier() != null) {
			order.setDossier(parameters.getDossier());
		} else {
			order.setNom(parameters.getNom());
			order.setPrenom(parameters.getPrenom());
			order.setEmail(parameters.getEmail());
		}
		OrderEntity orderSaved = orderDao.save(order);

		StringBuilder comment = new StringBuilder();
		if(StringUtils.isNotBlank(orderSaved.getComment())) {
			comment.append(orderSaved.getComment());
		}
		for (ProductUi productUi : parameters.getPanier()) {
			ProductEntity product = productDao.get(productUi.getId());
			int quantite = productUi.getQuantite();
			if(quantite > product.getStock()) {
				quantite = product.getStock();
				comment.append("attention pré-commande pour ").append(product.getTitle()).append("\n");
			}
			product.setStock(product.getStock() - quantite);
			OrderProductEntity orderProduct = new OrderProductEntity();
			orderProduct.setOrder(orderSaved.getId());
			orderProduct.setProduct(product.getId());
			orderProduct.setQuantity(productUi.getQuantite());
			orderProductDao.save(orderProduct);
		}
		orderSaved.setComment(comment.toString());
		orderDao.save(orderSaved);
		sendConfirmation(orderSaved);
	}

	private void sendConfirmation(OrderEntity order) {
		String email = order.getEmail();
		String emailSecondaire = null;
		if (order.getDossier() != null) {
			DossierEntity dossier = dossierDao.get(order.getDossier());
			email = dossier.getEmail();
			emailSecondaire = dossier.getEmailsecondaire();
		}

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("webmaster@asptt-toulouse-natation.com", "ASPTT Toulouse Natation"));
			Address[] replyTo = {
					new InternetAddress("contact@asptt-toulouse-natation.com", "ASPTT Toulouse Natation") };
			msg.setReplyTo(replyTo);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress("contact@asptt-toulouse-natation.com"));
			if (StringUtils.isNotBlank(emailSecondaire)) {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(emailSecondaire));
			}

			// Products
			List<OrderProductEntity> orderProducts = orderProductDao.findByOrder(order.getId());
			StringBuilder message = new StringBuilder(
					"Madame, Monsieur,<p>Nous avons le plaisir de vous confirmer la pré-commande de vos calendriers.<br />"
							+ "Nous vous tiendrons informé dès que votre commande sera validée. <br />"
							+ "<p>Commande n°" + order.getId() + "<br /><table>");
			int countProduct = 0;
			int total = 0;
			for (OrderProductEntity orderProduct : orderProducts) {

				ProductEntity product = productDao.get(orderProduct.getProduct());
				message.append("<tr><td>").append(product.getTitle()).append("</td><td>")
						.append(orderProduct.getQuantity()).append("</td></td>");
				int productPrice = 0;
				for (int i = 1; i <= orderProduct.getQuantity(); i++) {
					countProduct++;
					if (countProduct <= 2) {
						productPrice+=product.getPrice();
					} else if (countProduct > 2 && countProduct <= 4) {
						productPrice+=product.getPrice2();
					} else {
						productPrice+=product.getPrice3();
					}
				}
				total+=productPrice;
				message.append(productPrice).append("</td></tr>");
			}
			message.append("<td></td><td>").append(countProduct).append("</td><td>").append(total).append("</td>");
			message.append("</table>");
			message.append("<p>Sportivement,<br />" + "Le secrétariat,<br />" + "ASPTT Grand Toulouse Natation<br />"
					+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
			htmlPart.setContent(message.toString(), "text/html");
			mp.addBodyPart(htmlPart);

			msg.setSubject("ASPTT Toulouse Natation - Confirmation commande", "UTF-8");
			msg.setContent(mp);
			Transport.send(msg);
		} catch (MessagingException | UnsupportedEncodingException e) {
			LOG.log(Level.SEVERE, "Erreur pour l'e-mail: " + email, e);
		}
	}
	
	@Path("/product/update")
	@POST
	public void updateProduct(ProductEntity product) {
		productDao.save(product);
	}
	
	@Path("/product/delete/{product}")
	@DELETE
	public void removeProduct(@PathParam("product") Long product) {
		productDao.delete(product);
	}
	
	@Path("/product/load")
	@POST
	public void loadProducts(String products) {
		Scanner scanner = new Scanner(products);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] lineSplitted = line.split(";");
			ProductEntity product = new ProductEntity();
			String title = lineSplitted[0] + " - " + lineSplitted[1] + " - " + lineSplitted[2];
			product.setTitle(title);
			product.setDescription(title + " encadrés par " + lineSplitted[3]);
			product.setStock(10);
			product.setImage("img/calendriers/" + lineSplitted[4].replaceAll(" ", "") + ".jpg");
			product.setPrice(5);
			product.setPrice2(4);
			product.setPrice3(3);
			productDao.save(product);
		}
		scanner.close();
	}
	
	@Path("orders")
	@GET
	public List<OrderUi> orders() {
		List<OrderEntity> entities = orderDao.getAll();
		List<OrderUi> uis = new ArrayList<>(entities.size());
		for(OrderEntity entity: entities) {
			OrderUi ui = new OrderUi();
			ui.setOrder(entity);
			List<OrderProductEntity> orderProducts = orderProductDao.findByOrder(entity.getId());
			for(OrderProductEntity orderProduct: orderProducts) {
				ui.addProduit(productDao.get(orderProduct.getProduct()), orderProduct.getQuantity());
			}
			uis.add(ui);
		}
		Collections.sort(uis, new Comparator<OrderUi>() {

			@Override
			public int compare(OrderUi o1, OrderUi o2) {
				return o1.getOrder().getUpdated().compareTo(o2.getOrder().getUpdated());
			}
		});
		return uis;
	}
	
	@Path("/import")
	@POST
	public void importCsv(String data) {
		Scanner scanner = new Scanner(data);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] split = line.split(";");
			ProductEntity product = new ProductEntity();
			product.setPrice(5);
			product.setPrice2(4);
			product.setPrice3(4);
			product.setStock(100);
			product.setTitle(StringUtils.capitalize(StringUtils.lowerCase(split[1])) +  " - " + StringUtils.capitalize(StringUtils.lowerCase(split[2])) + " " + StringUtils.lowerCase(split[3]));
			product.setDescription("Encadré par " + StringUtils.capitalize(StringUtils.lowerCase(split[4]).replace("1.jpg", "")));
			if(split.length > 5) {
				product.setImage(split[6]);
			}
			productDao.save(product);
		}
		scanner.close();
	}
}