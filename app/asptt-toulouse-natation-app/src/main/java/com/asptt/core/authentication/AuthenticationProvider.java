package com.asptt.core.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		AuthenticationService service = new AuthenticationService();
		AuthenticationParameters authenticationParameters = new AuthenticationParameters();
		authenticationParameters.setEmail(email);
		authenticationParameters.setPassword(password);
		LoginResult result = service.login(authenticationParameters);
		if(result.isLogged()) {
			List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ADMIN"));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, password, grantedAuths);
			auth.setDetails(result);
			return auth;
		} else {
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.equals(UsernamePasswordAuthenticationToken.class);
	}

}
