package com.asptt.core.boutique;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
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
import javax.ws.rs.core.Response;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.joda.time.DateTime;

import com.asptt.core.inscription.DossierService;
import com.asptt.core.server.dao.boutique.OrderDao;
import com.asptt.core.server.dao.boutique.OrderProductDao;
import com.asptt.core.server.dao.boutique.ProductDao;
import com.asptt.core.server.dao.entity.boutique.OrderEntity;
import com.asptt.core.server.dao.entity.boutique.OrderProductEntity;
import com.asptt.core.server.dao.entity.boutique.OrderStatusEnum;
import com.asptt.core.server.dao.entity.boutique.ProductEntity;
import com.asptt.core.server.dao.entity.field.DossierEntityFields;
import com.asptt.core.server.dao.entity.inscription.DossierEntity;
import com.asptt.core.server.dao.entity.inscription.DossierStatutEnum;
import com.asptt.core.server.dao.inscription.DossierDao;
import com.asptt.core.server.dao.search.CriterionDao;
import com.asptt.core.server.dao.search.Operator;

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
			DossierEntity dossier = dossierDao.get(parameters.getDossier());
			order.setEmail(dossier.getEmail());
			order.setNom(dossier.getParent1Nom());
		} else {
			order.setNom(parameters.getNom());
			order.setPrenom(parameters.getPrenom());
			order.setEmail(parameters.getEmail());
		}
		OrderEntity orderSaved = orderDao.save(order);

		StringBuilder comment = new StringBuilder();
		if (StringUtils.isNotBlank(orderSaved.getComment())) {
			comment.append(orderSaved.getComment());
		}
		Set<Long> added = new HashSet<>();
		for (ProductUi productUi : parameters.getPanier()) {
			if (!added.contains(productUi.getId())) {
				ProductEntity product = productDao.get(productUi.getId());
				int quantite = productUi.getQuantite();
				if (quantite > product.getStock()) {
					quantite = product.getStock();
					comment.append("attention pré-commande pour ").append(product.getTitle()).append("\n");
				}
				product.setStock(product.getStock() - quantite);
				OrderProductEntity orderProduct = new OrderProductEntity();
				orderProduct.setOrder(orderSaved.getId());
				orderProduct.setProduct(product.getId());
				orderProduct.setQuantity(productUi.getQuantite());
				orderProductDao.save(orderProduct);
				added.add(product.getId());
			}
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
			msg.setFrom(new InternetAddress("ecole.natation.toulouse@gmail.com", "Toulouse Natation by ASPTT"));
			Address[] replyTo = {
					new InternetAddress("natation.toulouse@asptt.com", "Toulouse Natation by ASPTT") };
			msg.setReplyTo(replyTo);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			if (StringUtils.isNotBlank(emailSecondaire)) {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(emailSecondaire));
			}

			// Products
			List<OrderProductEntity> orderProducts = orderProductDao.findByOrder(order.getId());
			StringBuilder message = new StringBuilder(
					"Madame, Monsieur,<p>Nous avons le plaisir de vous confirmer la pré-commande de vos photos.<br />"
							+ "Nous vous tiendrons informé dès que votre commande sera validée. <br />"
							+ "<p>Commande n°" + order.getId() + "<br /><table>");
			int countProduct = 0;
			int total = 0;
			message.append("<tr><td>Article</td><td>Quantité</td><td>Prix</td>");
			for (OrderProductEntity orderProduct : orderProducts) {
				ProductEntity product = productDao.get(orderProduct.getProduct());
				message.append("<tr><td>").append(product.getTitle()).append("</td><td>")
						.append(orderProduct.getQuantity()).append("</td></td>");
				int productPrice = 0;
				for (int i = 1; i <= orderProduct.getQuantity(); i++) {
					countProduct++;
					if (countProduct < 2) {
						productPrice += product.getPrice();
					} else if (countProduct >= 2 && countProduct <= 4) {
						productPrice += product.getPrice2();
					} else {
						productPrice += product.getPrice3();
					}
				}
				total += productPrice;
				message.append(productPrice).append("</td></tr>");
			}
			message.append("<td>Total</td><td>").append(countProduct).append("</td><td>").append(total).append("</td>");
			message.append("</table>");
			message.append("<p>Sportivement,<br />" + "Le secrétariat,<br />" + "Toulouse Natation by ASPTT<br />"
					+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
			htmlPart.setContent(message.toString(), "text/html");
			mp.addBodyPart(htmlPart);

			msg.setSubject("Toulouse Natation by ASPTT - Confirmation commande", "UTF-8");
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

	@Path("/order/update")
	@POST
	public void updateOrder(OrderEntity order) {
		orderDao.save(order);
		orderProductDao.deleteProducts(order.getId());
		List<ProductEntity> products = null;
		for (ProductEntity product : products) {
			OrderProductEntity orderProduct = new OrderProductEntity();
			orderProduct.setOrder(order.getId());
			orderProduct.setProduct(product.getId());
			orderProduct.setQuantity(1);
			orderProductDao.save(orderProduct);
		}
	}

	@Path("/order/delete/{order}")
	@DELETE
	public void removeOrder(@PathParam("order") Long order) {
		orderProductDao.deleteProducts(order);
		orderDao.delete(order);
	}

	@Path("/order/delete/{order}/{product}")
	@DELETE
	public void removeOrderProduct(@PathParam("order") Long order, @PathParam("product") Long product) {
		List<OrderProductEntity> orderProducts = orderProductDao.findByOrderProduct(order, product);
		for(OrderProductEntity orderProduct : orderProducts) {
			if(orderProduct.getQuantity() == 1) {
				orderProductDao.delete(orderProduct);
			} else {
				orderProduct.setQuantity(orderProduct.getQuantity() -1);
				orderProductDao.save(orderProduct);
			}
		}
	}

	@Path("/product/load")
	@POST
	public void loadProducts(String products) {
		Scanner scanner = new Scanner(products);
		while (scanner.hasNextLine()) {
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
		for (OrderEntity entity : entities) {
			DossierEntity dossier = dossierDao.get(entity.getDossier());
			if (dossier != null) {
				entity.setEmail(dossier.getEmail());
				entity.setNom(dossier.getParent1Nom());
			}
			OrderUi ui = new OrderUi();
			ui.setOrder(entity);
			List<OrderProductEntity> orderProducts = orderProductDao.findByOrder(entity.getId());
			for (OrderProductEntity orderProduct : orderProducts) {
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
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] split = line.split(";");
			ProductEntity product = new ProductEntity();
			product.setPrice(5);
			product.setPrice2(4);
			product.setPrice3(4);
			product.setStock(100);
			product.setTitle(StringUtils.capitalize(StringUtils.lowerCase(split[1])) + " - "
					+ StringUtils.capitalize(StringUtils.lowerCase(split[2])) + " " + StringUtils.lowerCase(split[3]));
			product.setDescription(
					"Encadré par " + StringUtils.capitalize(StringUtils.lowerCase(split[4]).replace("1.jpg", "")));
			if (split.length > 5) {
				product.setImage(split[6]);
			}
			productDao.save(product);
		}
		scanner.close();
	}
	
	@Path("/order/validate")
	@POST
	public void validateOrder(Long orderId) {
		OrderEntity order = orderDao.get(orderId);
		order.setStatus(OrderStatusEnum.VALIDATED.name());
		orderDao.save(order);
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
			msg.setFrom(new InternetAddress("ecole.natation.toulouse@gmail.com", "Toulouse Natation by ASPTT"));
			Address[] replyTo = {
					new InternetAddress("natation.toulouse@asptt", "Toulouse Natation by ASPTT") };
			msg.setReplyTo(replyTo);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress("natation.toulouse@asptt.com"));
			if (StringUtils.isNotBlank(emailSecondaire)) {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(emailSecondaire));
			}

			// Products
			List<OrderProductEntity> orderProducts = orderProductDao.findByOrder(order.getId());
			StringBuilder message = new StringBuilder(
					"Madame, Monsieur,<p>Nous avons le plaisir de vous informer que vos photos sont en cours d'impression.<br />"
							+ "Les photos seront disponibles à partir du 5 mars 2018. Le paiement s'effectuera de préférence par chèque. <br />"
							+ "<p>Commande n°" + order.getId() + "<br /><table>");
			int countProduct = 0;
			int total = 0;
			message.append("<tr><td>Article</td><td>Quantité</td><td>Prix</td>");
			for (OrderProductEntity orderProduct : orderProducts) {

				ProductEntity product = productDao.get(orderProduct.getProduct());
				message.append("<tr><td>").append(product.getTitle()).append("</td><td>")
						.append(orderProduct.getQuantity()).append("</td></td>");
				int productPrice = 0;
				for (int i = 1; i <= orderProduct.getQuantity(); i++) {
					countProduct++;
					if (countProduct < 2) {
						productPrice += product.getPrice();
					} else if (countProduct >= 2 && countProduct <= 4) {
						productPrice += product.getPrice2();
					} else {
						productPrice += product.getPrice3();
					}
				}
				total += productPrice;
				message.append(productPrice).append("</td></tr>");
			}
			message.append("<td>Total</td><td>").append(countProduct).append("</td><td>").append(total).append("</td>");
			message.append("</table>");
			message.append("<p>Sportivement,<br />" + "Le secrétariat,<br />" + "Toulouse Natation by ASPTT<br />"
					+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
			htmlPart.setContent(message.toString(), "text/html");
			mp.addBodyPart(htmlPart);

			msg.setSubject("Toulouse Natation by ASPTT - Commande en cours de préparation", "UTF-8");
			msg.setContent(mp);
			Transport.send(msg);
		} catch (MessagingException | UnsupportedEncodingException e) {
			LOG.log(Level.SEVERE, "Erreur pour l'e-mail: " + email, e);
		}
	}
	
	@Path("excel")
	@GET
	@Produces("text/csv; charset=UTF-8")
	public Response extraction() {
		List<ProductEntity> produits = productDao.getAll();
		StrBuilder extractionAsString = new StrBuilder();
		for(ProductEntity produit : produits) {
			List<OrderProductEntity> orders = orderProductDao.findByProduct(produit.getId());
			int quantite = 0;
			for(OrderProductEntity order : orders) {
				quantite+=order.getQuantity();
			}
			if(quantite > 0) {
				extractionAsString.append(produit.getImage().split("/")[2]).append(",");
				extractionAsString.append(quantite).append(",");
				extractionAsString.appendNewLine();
			}
		}
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			out.write(extractionAsString.toString().getBytes("UTF-8"));

			String contentDisposition = "attachment;filename=extraction.csv;";
			return Response.ok(out.toByteArray(), "text/csv").header("content-disposition", contentDisposition).build();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Erreur when writing response (" + extractionAsString + ")", e);
			return Response.serverError().build();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LOG.log(Level.SEVERE, "Erreur when writing response (" + extractionAsString + ")", e);
					return Response.serverError().build();
				}
			}
		}
	}
	
	@Path("/lancer-boutique")
	@GET
	public int lancerBoutique() {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(2);
		criteria.add(
				new CriterionDao<String>(DossierEntityFields.STATUT, DossierStatutEnum.INSCRIT.name(), Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON, DossierService.NEW_SAISON, Operator.EQUAL));
		List<DossierEntity> entities = dossierDao.find(criteria);
		int count = 0;
		for (DossierEntity dossier : entities) {
			count++;
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			try {
				Multipart mp = new MimeMultipart();
				MimeBodyPart htmlPart = new MimeBodyPart();

				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("ecole.natation.toulouse@gmail.com", "Toulouse Natation by ASPTT"));
				Address[] replyTo = {
						new InternetAddress("natation.toulouse@asptt.com", "Toulouse Natation by ASPTT") };
				msg.setReplyTo(replyTo);
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(dossier.getEmail()));
				if (StringUtils.isNotBlank(dossier.getEmailsecondaire())) {
					msg.addRecipient(Message.RecipientType.CC, new InternetAddress(dossier.getEmailsecondaire()));
				}

				StringBuilder message = new StringBuilder(
						"Madame, Monsieur,<p>Les photos de groupe de la saison 2017-2018 sont prêtes à être commandés.<br/>Les commandes s'effectuent en ligne et le paiement par chèque. A réception du chèque vous recevrez la confirmation de la commande.<br />Les premières commandes seront disponibles à partir du 5 mars 2018.<br />Pour rappel, vos identifiants de connexion :</p>");
				message.append(
						"<p>Identifiant / mot de passe : " + dossier.getEmail() + " / " + dossier.getMotdepasse())
						.append("<br /><a href=\"http://boutique.asptt-toulouse-natation.com/\">http://boutique.asptt-toulouse-natation.com</a></p>");
				message.append("<p>Sportivement,<br />" + "Le secrétariat,<br />" + "Toulouse Natation by ASPTT<br />"
						+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
				htmlPart.setContent(message.toString(), "text/html");
				mp.addBodyPart(htmlPart);

				msg.setSubject("Toulouse Natation by ASPTT - Photos de groupe", "UTF-8");
				msg.setContent(mp);
				Transport.send(msg);
			} catch (MessagingException | UnsupportedEncodingException e) {
				LOG.log(Level.SEVERE, "Erreur pour l'e-mail: " + dossier.getEmail(), e);
			}
		}

		// Rapport
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("ecole.natation.toulouse@gmail.com", "Toulouse Natation by ASPTT"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("natation.toulouse@asptt.com"));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress("michael.kargbo@gmail.com"));

			StrBuilder message = new StrBuilder("<p>").append(count).append(" dossiers.</p>");
			htmlPart.setContent(message.toString(), "text/html");
			mp.addBodyPart(htmlPart);

			msg.setSubject("Rapport  - lancement boutique", "UTF-8");
			msg.setContent(mp);
			Transport.send(msg);
		} catch (MessagingException | UnsupportedEncodingException e) {
			LOG.log(Level.SEVERE, "Erreur pour l'e-mail de rapport", e);
		}

		LOG.log(Level.WARNING, count + " dossiers boutique");
		return count;
	}
}