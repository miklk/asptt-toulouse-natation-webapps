package com.asptttoulousenatation.client.userspace.admin.event;

import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.google.gwt.event.shared.GwtEvent;

public class UpdateContentEvent<O extends Object> extends GwtEvent<UpdateContentEventHandler> {
	
	public static final Type<UpdateContentEventHandler> TYPE = new Type<UpdateContentEventHandler>();
	
	private MenuItems action;
	private O object;
	
	public UpdateContentEvent() {
		
	}

	public UpdateContentEvent(MenuItems pAction) {
		action = pAction;
	}

	public UpdateContentEvent(MenuItems pAction, O pObject) {
		action = pAction;
		object = pObject;
	}

	public MenuItems getAction() {
		return action;
	}


	public void setAction(MenuItems pAction) {
		action = pAction;
	}

	public O getObject() {
		return object;
	}

	public void setObject(O pObject) {
		object = pObject;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UpdateContentEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateContentEventHandler pHandler) {
		pHandler.updateContent(this);
	}
}