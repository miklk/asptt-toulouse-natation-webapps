package com.asptttoulousenatation.core.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginRequiredServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6542746886471939627L;

	@Override
	protected void doGet(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		pResp.sendRedirect("/#/page/login");
	}

	@Override
	protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		doGet(pReq, pResp);
	}	
}