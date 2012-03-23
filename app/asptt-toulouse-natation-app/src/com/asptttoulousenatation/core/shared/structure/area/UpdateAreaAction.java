package com.asptttoulousenatation.core.shared.structure.area;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateAreaAction implements Action<UpdateAreaResult> {
	private Long id;
	private String title;
	private short order;
	
	public UpdateAreaAction() {
		
	}

	public UpdateAreaAction(Long pId, String pTitle, short pOrder) {
		super();
		id = pId;
		title = pTitle;
		order = pOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
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