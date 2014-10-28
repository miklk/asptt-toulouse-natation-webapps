package com.asptttoulousenatation.core.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.asptttoulousenatation.core.server.dao.entity.field.UserEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GoogleAccountsAuthenticationEntryPoint implements
		AuthenticationEntryPoint {
	
	private static final Logger LOG = Logger.getLogger(GoogleAccountsAuthenticationEntryPoint.class.getSimpleName());

	@Override
	public void commence(HttpServletRequest pRequest,
			HttpServletResponse pResponse, AuthenticationException pException)
			throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			String email = user.getEmail();
			LOG.severe(email);
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			criteria.add(new CriterionDao<String>(UserEntityFields.EMAILADDRESS, email, Operator.EQUAL));
			UserDao userDao = new UserDao();
			List<UserEntity> userEntities = userDao.find(criteria);
			if(CollectionUtils.isNotEmpty(userEntities)) {
				UserEntity authenticateUser = userEntities.get(0);
				LOG.severe(authenticateUser.getEmailaddress());
				if(!authenticateUser.getProfiles().contains(ProfileEnum.ADMIN.name())) {
					pResponse.sendRedirect("/#/page/no-rights");
				} else {
					LOG.severe("Admin");
				}
			} else {
				pResponse.sendRedirect("/#/page/unknow-user");
			}
		} else {
			pResponse.sendRedirect("/#/page/login");
		}
	}

}
