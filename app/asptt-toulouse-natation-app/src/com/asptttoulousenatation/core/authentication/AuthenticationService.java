package com.asptttoulousenatation.core.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.asptttoulousenatation.core.server.dao.entity.field.UserEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.user.UserAuthorizationEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.user.UserAuthorizationDao;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.server.dao.user.UserDataDao;
import com.asptttoulousenatation.server.util.Utils;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Path("/authentication")
@Produces("application/json")
public class AuthenticationService {

	@Context
	private UriInfo uriInfo;
	@Context
	private HttpServletRequest request;

	private UserAuthorizationDao userAuthorizationDao = new UserAuthorizationDao();
	
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
	@GET
	public LoginResult login(@QueryParam("email") String pEmail,
			@QueryParam("password") String pPassword) {
		LoginResult result = new LoginResult();
		if (StringUtils.isNotBlank(pEmail) && StringUtils.isNotBlank(pPassword)) {
			// Encrypt password
			String lEncryptedPassword = null;
			try {
				MessageDigest lMessageDigest = Utils.getMD5();
				lEncryptedPassword = new String(lMessageDigest.digest(pPassword
						.getBytes()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
					2);
			lCriteria.add(new CriterionDao<String>(
					UserEntityFields.EMAILADDRESS, pEmail, Operator.EQUAL));
			lCriteria.add(new CriterionDao<String>(UserEntityFields.PASSWORD,
					lEncryptedPassword, Operator.EQUAL));
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
				List<UserAuthorizationEntity> authorizations = userAuthorizationDao.getAuthorization(user.getId());
				for(UserAuthorizationEntity authorization: authorizations) {
					result.addAuthorization(authorization.getAccess());
				}
			} else {
				result.setLogged(false);
			}
		} else {
			result.setLogged(false);
		}
		return result;
	}
	
	@Path("/isAuthenticated")
	@GET
	public LoginResult isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAuthenticated = authentication!= null && !(authentication instanceof AnonymousAuthenticationToken) &&
				 SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		final LoginResult result;
		if(isAuthenticated) {
			result = (LoginResult) SecurityContextHolder.getContext().getAuthentication().getDetails();
		} else {
			result = new LoginResult();
		}
		result.setLogged(isAuthenticated);
		return result;
	}
}
