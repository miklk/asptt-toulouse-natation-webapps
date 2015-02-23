package com.asptttoulousenatation.core.server.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.entity.field.UserEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.server.entity.UserTransformer;
import com.asptttoulousenatation.core.shared.user.AuthenticationAction;
import com.asptttoulousenatation.core.shared.user.AuthenticationResult;
import com.asptttoulousenatation.server.util.Utils;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class AuthenticationActionHandler implements
		ActionHandler<AuthenticationAction, AuthenticationResult> {

	private UserDao userDao = new UserDao();
	private UserTransformer userTransformer = new UserTransformer();
	
	@Inject
	private Provider<HttpSession> session;
	
	public AuthenticationResult execute(AuthenticationAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Encrypt password
		String lEncryptedPassword = null;
		try {
			MessageDigest lMessageDigest = Utils.getMD5();
			lEncryptedPassword = new String(lMessageDigest.digest(pAction.getPassword().getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(2);
		CriterionDao<String> lEmailAddressCriterion = new CriterionDao<String>(UserEntityFields.EMAILADDRESS, pAction.getEmailAddress(), Operator.EQUAL);
		lCriteria.add(lEmailAddressCriterion);
		CriterionDao<String> lPasswordCriterion = new CriterionDao<String>(UserEntityFields.PASSWORD, lEncryptedPassword, Operator.EQUAL);
		lCriteria.add(lPasswordCriterion);
		List<UserEntity> lUser = userDao.find(lCriteria);
		final AuthenticationResult lAuthenticationResult = new AuthenticationResult();
		if(lUser.isEmpty()) {
			lAuthenticationResult.setAuthenticated(false);
			session.get().setAttribute("isAuthenticated", false);
			session.get().setAttribute("userObject", null);
		}
		else {
			lAuthenticationResult.setAuthenticated(true);
			lAuthenticationResult.setUser(userTransformer.toUi(lUser.get(0)));
			session.get().setAttribute("isAuthenticated", true);
			session.get().setAttribute("userObject", lUser.get(0));
		}
		
		return lAuthenticationResult;
	}

	public Class<AuthenticationAction> getActionType() {
		return AuthenticationAction.class;
	}

	public void rollback(AuthenticationAction pArg0,
			AuthenticationResult pArg1, ExecutionContext pArg2)
			throws DispatchException {
		// TODO Auto-generated method stub

	}

}
