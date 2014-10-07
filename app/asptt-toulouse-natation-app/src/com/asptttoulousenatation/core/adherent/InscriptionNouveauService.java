package com.asptttoulousenatation.core.adherent;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionNouveauEntity;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionNouveauDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

@Path("/nouveau")
@Consumes("application/json")
public class InscriptionNouveauService {

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private InscriptionNouveauDao dao = new InscriptionNouveauDao();
	private Inscription2Dao inscription2Dao = new Inscription2Dao();

	@PUT
	public void nouveau(@QueryParam("email") String pEmail) {
		if (StringUtils.isNotBlank(pEmail)) {
			InscriptionNouveauEntity entity = new InscriptionNouveauEntity();
			entity.setEmail(pEmail);
			dao.save(entity);
		}
	}
	
	@GET
	public NouveauPasswordResult motDePasse(@QueryParam("email") String pEmail) {
		NouveauPasswordResult result = new NouveauPasswordResult();
		//Test existence de l'adresse e-mail
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(InscriptionEntityFields.EMAIL,
				pEmail, Operator.EQUAL));
		List<InscriptionEntity2> entities = inscription2Dao.find(criteria);
		if(CollectionUtils.isNotEmpty(entities)) {
			result.setExist(true);
		} else {
			//Determine mot de passee
			String code = RandomStringUtils.randomNumeric(4);
			InscriptionEntity2 nouveau = new InscriptionEntity2();
			nouveau.setEmail(pEmail);
			nouveau.setMotdepasse(code);
			nouveau.setNouveau(true);
			inscription2Dao.save(nouveau);
			
			try {
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);

				Multipart mp = new MimeMultipart();
				MimeBodyPart htmlPart = new MimeBodyPart();
				String msgBody = "Madame, Monsieur,<br />"
						+ "Vous pouvez maintenant accéder au formulaire d'inscription en utilisant le code suivant: "
						+ "<b>" + nouveau.getMotdepasse() + "</b>"
						+ ".<br />"
						+ "<a href=\"http://www.asptt-toulouse-natation.com/#/page/Inscription\">Inscription en ligne - ASPTT Toulouse Natation</a>"
						+ "<p>Sportivement,<br />ASPTT Toulouse Natation</p>";

				htmlPart.setContent(msgBody, "text/html");
				mp.addBodyPart(htmlPart);
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(
						"webmaster@asptt-toulouse-natation.com",
						"ASPTT Toulouse Natation"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						nouveau.getEmail()));
				msg.setSubject("Votre compte web a été créé.", "UTF-8");
				msg.setContent(mp);
				Transport.send(msg);
			} catch (AddressException e) {
				// ...
			} catch (MessagingException e) {
				// ...
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}