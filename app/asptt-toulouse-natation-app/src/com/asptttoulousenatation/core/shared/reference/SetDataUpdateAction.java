package com.asptttoulousenatation.core.shared.reference;

import net.customware.gwt.dispatch.shared.Action;

public class SetDataUpdateAction implements Action<SetDataUpdateResult> {

	private Class<?> kind;
	private Boolean updated;
	
	public SetDataUpdateAction() {
		
	}

	public SetDataUpdateAction(Class<?> pKind, Boolean pUpdated) {
		kind = pKind;
		updated = pUpdated;
	}

	public Class<?> getKind() {
		return kind;
	}

	public void setKind(Class<?> pKind) {
		kind = pKind;
	}

	public Boolean getUpdated() {
		return updated;
	}

	public void setUpdated(Boolean pUpdated) {
		updated = pUpdated;
	}
}