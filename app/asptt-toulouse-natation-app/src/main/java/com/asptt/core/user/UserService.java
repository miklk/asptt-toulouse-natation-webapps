package com.asptt.core.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.asptt.core.server.dao.entity.field.UserDataEntityFields;
import com.asptt.core.server.dao.entity.field.UserEntityFields;
import com.asptt.core.server.dao.entity.user.UserAuthorizationEntity;
import com.asptt.core.server.dao.entity.user.UserDataEntity;
import com.asptt.core.server.dao.entity.user.UserEntity;
import com.asptt.core.server.dao.search.CriterionDao;
import com.asptt.core.server.dao.search.Operator;
import com.asptt.core.server.dao.user.UserAuthorizationDao;
import com.asptt.core.server.dao.user.UserDao;
import com.asptt.core.server.dao.user.UserDataDao;
import com.asptt.core.shared.user.UserUi;
import com.asptt.core.util.Utils;

@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserService {

	private static final Logger LOG = Logger.getLogger(UserService.class);

	private UserDao userDao = new UserDao();
	private UserDataDao userDataDao = new UserDataDao();
	private UserAuthorizationDao userAuthorizationDao = new UserAuthorizationDao();
	private UserTransformer userTransformer = new UserTransformer();
	private UserDataTransformer userDataTransformer = new UserDataTransformer();

	private static Set<String> AUTHORIZATIONS;

	static {
		AUTHORIZATIONS = new HashSet<>();
		AUTHORIZATIONS.add("ACCESS_INSCRIPTION");
		AUTHORIZATIONS.add("ACCESS_GROUPES");
		AUTHORIZATIONS.add("ACCESS_DOSSIERS");
		AUTHORIZATIONS.add("ACCESS_REMPLISSAGE");
		AUTHORIZATIONS.add("ACCESS_ENF");
		AUTHORIZATIONS.add("ACCESS_DOCUMENTS");
		AUTHORIZATIONS.add("ACCESS_USERS");
		AUTHORIZATIONS.add("ACCESS_SUIVI_NAGEURS");
		AUTHORIZATIONS.add("ACCESS_DOSSIERS_EMAIL");
		AUTHORIZATIONS.add("ACCESS_BOUTIQUE");
		AUTHORIZATIONS.add("ACCESS_SITE");
		AUTHORIZATIONS.add("ACCESS_ACTUALITES");
		AUTHORIZATIONS.add("ACCESS_PAGEEDITION");
		AUTHORIZATIONS.add("ACCESS_NAGEUR_EFFECTIF");
		AUTHORIZATIONS.add("ACCESS_SALARIE_ACTIVITE_SUIVI");
		AUTHORIZATIONS.add("ACCESS_COMPETITION");
	}

	@GET
	public UserFindResult find(@QueryParam("search") String pSearch) {
		UserFindResult result = new UserFindResult();
		List<UserEntity> userEntities = new ArrayList<>();
		List<UserDataEntity> userDataEntities = new ArrayList<>();
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		if (StringUtils.isBlank(pSearch)) {
			userEntities.addAll(userDao.getAll());
		} else if (StringUtils.contains(pSearch, "@")) {// Email
			criteria.add(new CriterionDao<String>(UserEntityFields.EMAILADDRESS, pSearch, Operator.EQUAL));
			userEntities.addAll(userDao.find(criteria));
		} else {
			criteria.add(new CriterionDao<String>(UserDataEntityFields.LASTNAME, pSearch, Operator.EQUAL));
			userDataEntities.addAll(userDataDao.find(criteria));
			if (CollectionUtils.isEmpty(userDataEntities)) {
				criteria.clear();
				criteria.add(new CriterionDao<String>(UserDataEntityFields.FIRSTNAME, pSearch, Operator.EQUAL));
				userDataEntities.addAll(userDataDao.find(criteria));
			}
			for (UserDataEntity userData : userDataEntities) {
				List<CriterionDao<? extends Object>> userCriteria = new ArrayList<CriterionDao<? extends Object>>(1);
				userCriteria.add(new CriterionDao<Long>(UserEntityFields.DATA, userData.getId(), Operator.EQUAL));
				List<UserEntity> usersEntities = userDao.find(userCriteria);
				if (CollectionUtils.isNotEmpty(usersEntities)) {
					UserUi user = userTransformer.toUi(usersEntities.get(0));
					user.setUserData(userDataTransformer.toUi(userData));
					result.addUser(user);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(userEntities)) {
			for (UserEntity user : userEntities) {
				UserDataEntity userData = userDataDao.get(user.getUserData());
				UserUi userUi = userTransformer.toUi(user);
				userUi.setUserData(userDataTransformer.toUi(userData));
				List<UserAuthorizationEntity> authorizations = userAuthorizationDao.findByUser(user.getId());
				for (UserAuthorizationEntity authorization : authorizations) {
					userUi.addAccess(authorization.getAccess());
				}
				result.addUser(userUi);
			}
		}

		return result;
	}

	@Path("/create")
	@POST
	public UserCreateResult create(UserCreateAction pAction) {
		UserCreateResult result = new UserCreateResult();
		try {

			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			criteria.add(new CriterionDao<String>(UserEntityFields.EMAILADDRESS, pAction.getEmail(), Operator.EQUAL));
			List<UserEntity> users = userDao.find(criteria);
			final UserEntity userEntity;
			final boolean creation;
			if (CollectionUtils.isEmpty(users)) {
				userEntity = new UserEntity();
				creation = true;
			} else {
				userEntity = users.get(0);
				creation = false;
			}
			userEntity.setEmailaddress(pAction.getEmail());
			final String code;
			if (StringUtils.isNotBlank(pAction.getPassword())) {
				code = pAction.getPassword();
			} else {
				code = RandomStringUtils.randomNumeric(4);
			}
			LOG.info("Code: " + code);
			// Encrypt password
			MessageDigest lMessageDigest = Utils.getMD5();
			String encryptedPassword = new String(lMessageDigest.digest(code.getBytes()));
			userEntity.setPassword(encryptedPassword);
			userEntity.setValidated(true);

			final UserDataEntity userDataEntity;
			if (userEntity.getUserData() == null) {
				userDataEntity = new UserDataEntity();
			} else {
				userDataEntity = userDataDao.get(userEntity.getUserData());
			}

			userDataEntity.setFirstName(pAction.getPrenom());
			userDataEntity.setLastName(pAction.getNom());
			UserDataEntity userDataEntityCreated = userDataDao.save(userDataEntity);
			userEntity.setUserData(userDataEntityCreated.getId());
			UserEntity userSaved = userDao.save(userEntity);
			// Create authorizations
			userAuthorizationDao.deleteByUser(userSaved.getId());
			for (String access : pAction.getAuthorizations()) {
				if (AUTHORIZATIONS.contains(access)) {
					UserAuthorizationEntity authorization = new UserAuthorizationEntity();
					authorization.setUser(userSaved.getId());
					authorization.setAccess(access);
					userAuthorizationDao.save(authorization);
				}
			}
			result.setSuccess(true);

			if(creation) {
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);
	
				Multipart mp = new MimeMultipart();
				MimeBodyPart htmlPart = new MimeBodyPart();
				String msgBody = "Bienvenue à l'espace privé Toulouse Natation by ASPTT,<br />"
						+ "Vous pouvez maintenant accéder à l'espace privé Toulouse Natation by ASPTT en utilisant le code suivant: "
						+ "<b>" + code + "</b>" + ".<br />"
						+ "<a href=\"http://www.asptt-toulouse-natation.com/admin\">Espace privé</a>"
						+ "<p>Sportivement,<br />Toulouse Natation by ASPTT</p>";
	
				htmlPart.setContent(msgBody, "text/html");
				mp.addBodyPart(htmlPart);
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("webmaster@asptt-toulouse-natation.com", "Toulouse Natation by ASPTT"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(userEntity.getEmailaddress()));
				msg.setSubject("Votre compte web privé a été créé.", "UTF-8");
				msg.setContent(mp);
				Transport.send(msg);
			}
		} catch (MessagingException | UnsupportedEncodingException e) {
			LOG.error("Impossible d'envoyer le mot de passe par e-mail", e);
		} catch (NoSuchAlgorithmException e) {
			LOG.fatal("Error during user creation", e);
		}
		return result;
	}

	@Path("{user}")
	@DELETE
	public void remove(@PathParam("user") Long userId) {
		UserEntity user = userDao.get(userId);
		userAuthorizationDao.deleteByUser(user.getId());
		userDataDao.delete(user.getUserData());
		userDao.delete(userId);
	}

	@Path("/available-authorizations")
	@GET
	public Set<String> authorizations() {
		return AUTHORIZATIONS;
	}
	
	/**@Path("/default")
	@GET
	public void createDefault() {
		UserCreateAction action = new UserCreateAction();
		action.setEmail("root@root.com");
		action.setNom("Root");
		action.setPassword("0123");
		action.setAuthorizations(AUTHORIZATIONS);
		create(action);
	}**/
}