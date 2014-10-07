package com.asptttoulousenatation.core.authentication;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Path("/authentication")
@Produces("application/json")
public class AuthenticationService {

	@Context
	private UriInfo uriInfo;
	@Context
	private HttpServletRequest request;
	
	@Path("{openIdService}")
	@GET
	public AuthenticationResult authentication(@PathParam("openIdService")String pOpenIdService) {
		UserService userService = UserServiceFactory.getUserService();
		String authenticationUrl = "";
		switch(pOpenIdService) {
		case "google": authenticationUrl = userService.createLoginURL("/#/page/user-index", null, "https://www.google.com/accounts/o8/id", new HashSet<String>());
		break;
		default:
			userService.createLogoutURL("/#/page/logout");
		}
		AuthenticationResult result = new AuthenticationResult();
		result.setProviderUrl(authenticationUrl);
		return result;
	}
}
