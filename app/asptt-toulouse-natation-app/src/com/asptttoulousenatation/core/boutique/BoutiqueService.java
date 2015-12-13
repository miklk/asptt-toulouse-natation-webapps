package com.asptttoulousenatation.core.boutique;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
		OrderEntity OrderSaved = orderDao.save(order);

		for (ProductUi productUi : parameters.getPanier()) {
			ProductEntity product = productDao.get(productUi.getId());
			OrderProductEntity orderProduct = new OrderProductEntity();
			orderProduct.setOrder(OrderSaved.getId());
			orderProduct.setProduct(product.getId());
			orderProduct.setQuantity(productUi.getQuantite());
			orderProductDao.save(orderProduct);
		}

		sendConfirmation(order);
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

			StringBuilder message = new StringBuilder(
					"Madame, Monsieur,<p>Nous avons le plaisir de vous confirmer la pré-commande de vos calendriers.<br />"
							+ "Nous vous tiendrons informé dès que votre commande sera validée. <br />");
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
		return uis;
	}
}