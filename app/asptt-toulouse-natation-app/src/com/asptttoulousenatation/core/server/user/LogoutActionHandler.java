package com.asptttoulousenatation.core.server.user;

import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.shared.user.LogoutAction;
import com.asptttoulousenatation.core.shared.user.LogoutResult;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class LogoutActionHandler implements
		ActionHandler<LogoutAction, LogoutResult> {

	@Inject
	private Provider<HttpSession> sessionProvider;
	
	public LogoutResult execute(LogoutAction pAction, ExecutionContext pContext)
			throws DispatchException {
		sessionProvider.get().removeAttribute("isAuthenticated");
		return new LogoutResult();
	}

	public Class<LogoutAction> getActionType() {
		return LogoutAction.class;
	}

	public void rollback(LogoutAction pAction, LogoutResult pResult,
			ExecutionContext pContext) throws DispatchException {
	}
}