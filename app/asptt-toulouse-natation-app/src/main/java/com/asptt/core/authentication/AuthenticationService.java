package com.asptt.core.authentication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.asptt.core.server.dao.entity.field.UserEntityFields;
import com.asptt.core.server.dao.entity.user.UserAuthorizationEntity;
import com.asptt.core.server.dao.entity.user.UserDataEntity;
import com.asptt.core.server.dao.entity.user.UserEntity;
import com.asptt.core.server.dao.search.CriterionDao;
import com.asptt.core.server.dao.search.Operator;
import com.asptt.core.server.dao.user.UserAuthorizationDao;
import com.asptt.core.server.dao.user.UserDao;
import com.asptt.core.server.dao.user.UserDataDao;
import com.asptt.core.util.Utils;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Path("/authentication")
@Produces("application/json")
public class AuthenticationService {
	
	private static final Logger LOG = Logger.getLogger(AuthenticationService.class
			.getName());
	
	@Context
	private UriInfo uriInfo;
	@Context
	private HttpServletRequest request;

	private UserAuthorizationDao userAuthorizationDao = new UserAuthorizationDao();
	private UserDao userDao = new UserDao();
	
	@Path("{openIdService}")
	@GET
	public AuthenticationResult authentication(
			@PathParam("openIdService") String pOpenIdService) {
		UserService userService = UserServiceFactory.getUserService();
		String authenticationUrl = "";
		switch (pOpenIdService) {
		case "google":
			authenticationUrl = userService.createLoginURL("/authorization.do",
					null, "https://www.google.com/accounts/o8/id",
					new HashSet<String>());
			break;
		default:
			userService.createLogoutURL("/#/page/logout");
		}
		AuthenticationResult result = new AuthenticationResult();
		result.setProviderUrl(authenticationUrl);
		return result;
	}

	@Path("/login")
	@POST
	public LoginResult login(AuthenticationParameters parameters) {
		LoginResult result = new LoginResult();
		if (StringUtils.isNotBlank(parameters.getEmail()) && StringUtils.isNotBlank(parameters.getPassword())) {
			// Encrypt password
			String lEncryptedPassword = null;
			try {
				MessageDigest lMessageDigest = Utils.getMD5();
				lEncryptedPassword = new String(lMessageDigest.digest(parameters.getPassword()
						.getBytes()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
					2);
			lCriteria.add(new CriterionDao<String>(
					UserEntityFields.EMAILADDRESS, parameters.getEmail(), Operator.EQUAL));
//			lCriteria.add(new CriterionDao<String>(UserEntityFields.PASSWORD,
//					lEncryptedPassword, Operator.EQUAL));
			UserDao userDao = new UserDao();
			UserDataDao userDataDao = new UserDataDao();
			List<UserEntity> entities = userDao.find(lCriteria);
			if (CollectionUtils.isNotEmpty(entities)) {
				UserEntity user = entities.get(0);
				result.setEmail(user.getEmailaddress());
				UserDataEntity userDataEntity = userDataDao.get(user
						.getUserData());
				result.setNom(userDataEntity.getLastName());
				result.setPrenom(userDataEntity.getFirstName());
				result.setLogged(true);
				
				//Authorizations
				List<UserAuthorizationEntity> authorizations = userAuthorizationDao.findByUser(user.getId());
				for(UserAuthorizationEntity authorization: authorizations) {
					result.addAuthorization(authorization.getAccess());
				}
				UUID tokenId = UUID.randomUUID();
				String token = tokenId.toString();
				result.setToken(token);
				TokenManager.getInstance().tokens.put(token, user.getId());
			} else {
				result.setLogged(false);
			}
		} else {
			result.setLogged(false);
		}
		return result;
	}
	
	@Autowired
	private AuthenticationProvider authenticationProvider = new AuthenticationProvider();
	
	@Path("/isAuthenticated")
	@POST
	public LoginResult isAuthenticated(AuthenticationParameters parameters) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAuthenticated = authentication!= null && !(authentication instanceof AnonymousAuthenticationToken) &&
				 SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		final LoginResult result;
		if(isAuthenticated) {
			result = (LoginResult) SecurityContextHolder.getContext().getAuthentication().getDetails();
		} else {
			Authentication authenticationToken = new UsernamePasswordAuthenticationToken(parameters.getEmail(), parameters.getPassword());
			       authentication = authenticationProvider.authenticate(authenticationToken);
			        SecurityContextHolder.getContext().setAuthentication(authentication);
			result = (LoginResult) authentication.getDetails();
		}
		return result;
	}
	
	@Path("/forget/{email}")
	@GET
	public boolean forget(@PathParam("email") String email) {
		boolean result = false;
		try {

			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			criteria.add(new CriterionDao<String>(UserEntityFields.EMAILADDRESS, email, Operator.EQUAL));
			List<UserEntity> users = userDao.find(criteria);
			final UserEntity userEntity;
			if (CollectionUtils.isEmpty(users)) {
				result = false;
			} else {
				result = true;
				userEntity = users.get(0);
				final String code;
				code = RandomStringUtils.randomNumeric(4);
				// Encrypt password
				MessageDigest lMessageDigest = Utils.getMD5();
				String encryptedPassword = new String(lMessageDigest.digest(code.getBytes()));
				userEntity.setPassword(encryptedPassword);
				userDao.save(userEntity);

				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);

				Multipart mp = new MimeMultipart();
				MimeBodyPart htmlPart = new MimeBodyPart();
				String msgBody = "Votre nouveau mot de passe est : " + "<b>" + code + "</b>" + ".<br />"
						+ "<a href=\"http://www.asptt-toulouse-natation.com/admin\">Espace privé</a>"
						+ "<p>Sportivement,<br />Toulouse Natation by ASPTT</p>";

				htmlPart.setContent(msgBody, "text/html");
				mp.addBodyPart(htmlPart);
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("ecole.natation.toulouse@gmail.com", "Toulouse Natation by ASPTT"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(userEntity.getEmailaddress()));
				msg.setSubject("Réinitialisation du mot de passe", "UTF-8");
				msg.setContent(mp);
				Transport.send(msg);
			}
		} catch (MessagingException | UnsupportedEncodingException e) {
			LOG.log(Level.SEVERE, "Impossible d'envoyer le mot de passe par e-mail", e);
		} catch (NoSuchAlgorithmException e) {
			LOG.log(Level.SEVERE, "Error during user creation", e);
		}
		return result;
	}
	
	@Path("/isLogged/{token}")
	@GET
	public IsLoggedResult isLogged(@PathParam("token") String token) {
		final IsLoggedResult result = new IsLoggedResult();
		if(token != null) {
			result.setLogged(TokenManager.getInstance().contains(token));
		} else {
			result.setLogged(false);
		}
		return result;
	}
	
	@Path("/logout/{token}")
	@GET
	public int logout(@PathParam("token") String token) {
		TokenManager.getInstance().remove(token);
		return 0;
	}
	
	@Path("/logout-all")
	@GET
	public int logoutAll() {
		TokenManager.getInstance().clear();
		return 0;
	}
	
	@Path("/expire")
	@GET
	public int expire() {
		TokenManager.getInstance().expire(30);
		return 0;
	}
}
