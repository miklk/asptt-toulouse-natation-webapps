package com.asptttoulousenatation.core.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import com.asptttoulousenatation.core.security.SecurityConfig;

public class SecurityWebApplicationInitializer extends
		AbstractSecurityWebApplicationInitializer {

	public SecurityWebApplicationInitializer() {
		super(SecurityConfig.class);
	}
}
