package com.asptttoulousenatation.core.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;

import com.asptttoulousenatation.core.server.dao.entity.field.UserEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginRequiredServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6542746886471939627L;
	
	private static final Logger LOG = Logger.getLogger(LoginRequiredServlet.class.getName());

	private UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			String email = user.getEmail();
			LOG.severe(email);
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			criteria.add(new CriterionDao<String>(UserEntityFields.EMAILADDRESS, email, Operator.EQUAL));
			List<UserEntity> userEntities = userDao.find(criteria);
			if(CollectionUtils.isNotEmpty(userEntities)) {
				UserEntity authenticateUser = userEntities.get(0);
				LOG.severe(authenticateUser.getEmailaddress());
				if(!authenticateUser.getProfiles().contains(ProfileEnum.ADMIN.name())) {
					pResp.sendRedirect("/#/page/no-rights");
				} else {
					LOG.severe("Admin");
				}
			} else {
				pResp.sendRedirect("/#/page/unknow-user");
			}
		} else {
			pResp.sendRedirect("/#/page/login");
		}
	}

	@Override
	protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		doGet(pReq, pResp);
	}
}