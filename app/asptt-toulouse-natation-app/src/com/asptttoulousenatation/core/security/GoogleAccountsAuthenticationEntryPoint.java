package com.asptttoulousenatation.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GoogleAccountsAuthenticationEntryPoint implements
		AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest pRequest,
			HttpServletResponse pResponse, AuthenticationException pException)
			throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
		pResponse.sendRedirect(userService.createLoginURL(pRequest
				.getRequestURI()));
	}

}
