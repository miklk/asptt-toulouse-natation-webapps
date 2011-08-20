package com.asptttoulousenatation.core.server.config;

import net.customware.gwt.dispatch.server.guice.GuiceStandardDispatchServlet;

import com.google.inject.servlet.ServletModule;

public class DispatchServletModule extends ServletModule {

	public void configureServlets() {
		serve("/asptt_toulouse_natation_app/dispatch").with(GuiceStandardDispatchServlet.class);
	}
}
