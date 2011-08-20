package com.asptttoulousenatation.core.server.user;

import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.entity.UserTransformer;
import com.asptttoulousenatation.core.shared.user.IsAuthenticatedAction;
import com.asptttoulousenatation.core.shared.user.IsAuthenticatedResult;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class IsAuthenticatedActionHandler implements
		ActionHandler<IsAuthenticatedAction, IsAuthenticatedResult> {

	@Inject
	private Provider<HttpSession> session;
	
	private UserTransformer userTransformer = new UserTransformer();
	
	public IsAuthenticatedResult execute(IsAuthenticatedAction pAction,
			ExecutionContext pContext) throws DispatchException {
		Object lAttribute = session.get().getAttribute("isAuthenticated");
		if(lAttribute != null && ((Boolean) lAttribute)) {
			return new IsAuthenticatedResult(true, userTransformer.toUi((UserEntity) session.get().getAttribute("userObject")));
		}
		else {
			return new IsAuthenticatedResult(false, null);
		}
	}

	public Class<IsAuthenticatedAction> getActionType() {
		return IsAuthenticatedAction.class;
	}

	public void rollback(IsAuthenticatedAction pAction,
			IsAuthenticatedResult pResult, ExecutionContext pContext)
			throws DispatchException {
	}

}
