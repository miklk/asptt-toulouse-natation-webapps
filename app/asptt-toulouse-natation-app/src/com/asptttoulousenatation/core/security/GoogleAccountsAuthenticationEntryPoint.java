package com.asptttoulousenatation.core.security;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class GoogleAccountsAuthenticationEntryPoint implements
		AuthenticationEntryPoint {
	
	private static final Logger LOG = Logger.getLogger(GoogleAccountsAuthenticationEntryPoint.class.getSimpleName());

	@Override
	public void commence(HttpServletRequest pRequest,
			HttpServletResponse pResponse, AuthenticationException pException)
			throws IOException, ServletException {
		LOG.severe("entrypoint");
		pResponse.sendRedirect("/#/page/login");
	}

}
