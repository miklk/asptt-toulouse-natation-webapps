package com.asptttoulousenatation.core.shared.structure.area;


import net.customware.gwt.dispatch.shared.Action;

public class CreateAreaAction implements Action<CreateAreaResult> {

	private String title;
	private short order;
	
	public CreateAreaAction() {
		
	}

	public CreateAreaAction(String pTitle, short pOrder) {
		super();
		title = pTitle;
		order = pOrder;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public short getOrder() {
		return order;
	}

	public void setOrder(short pOrder) {
		order = pOrder;
	}
	
}