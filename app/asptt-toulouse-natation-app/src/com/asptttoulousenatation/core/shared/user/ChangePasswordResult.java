package com.asptttoulousenatation.core.shared.user;

import net.customware.gwt.dispatch.shared.Result;

public class ChangePasswordResult implements Result {

	private boolean result;
	private boolean notSame;
	
	public ChangePasswordResult() {
		result = false;
		notSame = false;
	}

	public ChangePasswordResult(boolean pResult) {
		this();
		result = pResult;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean pResult) {
		result = pResult;
	}

	public boolean isNotSame() {
		return notSame;
	}

	public void setNotSame(boolean pNotSame) {
		notSame = pNotSame;
	}
}