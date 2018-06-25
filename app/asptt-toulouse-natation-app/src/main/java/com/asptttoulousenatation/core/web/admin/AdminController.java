package com.asptttoulousenatation.core.web.admin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.security.core.context.SecurityContextHolder;

@Path("/admin")
public class AdminController {

	@GET
	public Boolean adminPage() {
		return SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated(); 
	}
}
