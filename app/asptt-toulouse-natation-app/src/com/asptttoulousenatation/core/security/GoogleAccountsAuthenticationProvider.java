package com.asptttoulousenatation.core.security;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.asptttoulousenatation.core.server.dao.entity.field.UserEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.google.appengine.api.users.User;

public class GoogleAccountsAuthenticationProvider implements
		AuthenticationProvider {

	private static final Logger LOG = Logger
			.getLogger(GoogleAccountsAuthenticationProvider.class
					.getSimpleName());

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		LOG.severe("provider");
		User googleUser = (User) authentication.getPrincipal();

		String email = googleUser.getEmail();
		LOG.severe(email);
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(UserEntityFields.EMAILADDRESS,
				email, Operator.EQUAL));
		UserDao userDao = new UserDao();
		List<UserEntity> userEntities = userDao.find(criteria);
		if (CollectionUtils.isNotEmpty(userEntities)) {
			UserEntity authenticatedUser = userEntities.get(0);
			LOG.severe(authenticatedUser.getEmailaddress());
			if (!authenticatedUser.getProfiles().contains(
					ProfileEnum.ADMIN.name())) {
				throw new BadCredentialsException(
						"Utilisateur non administrateur");
			} else {
				return new GaeUserAuthentication(authenticatedUser,
						authentication.getDetails());
			}
		} else {
			throw new UsernameNotFoundException("Utilisateur inconnu");
		}
	}

	public final boolean supports(Class<?> authentication) {
		return PreAuthenticatedAuthenticationToken.class
				.isAssignableFrom(authentication);
	}
}