package com.asptttoulousenatation.core.shared.reference;

import net.customware.gwt.dispatch.shared.Action;

public class IsDataUpdateAction implements Action<IsDataUpdateResult> {

	private Class<?> kind;
	
	public IsDataUpdateAction() {
		
	}

	public IsDataUpdateAction(Class<?> pKind) {
		kind = pKind;
	}

	public Class<?> getKind() {
		return kind;
	}

	public void setKind(Class<?> pKind) {
		kind = pKind;
	}
}