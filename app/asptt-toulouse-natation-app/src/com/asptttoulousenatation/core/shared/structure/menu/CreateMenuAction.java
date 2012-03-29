package com.asptttoulousenatation.core.shared.structure.menu;

import net.customware.gwt.dispatch.shared.Action;

public class CreateMenuAction implements Action<CreateMenuResult> {

	private String title;
	private String summary;
	private String content;
	private short order;
	private Long area;
	private Long parent;
	
	public CreateMenuAction() {
		
	}

	public CreateMenuAction(String pTitle, String pSummary, String pContent,
			short pOrder, Long pArea, Long pParent) {
		super();
		title = pTitle;
		summary = pSummary;
		content = pContent;
		order = pOrder;
		area = pArea;
		parent = pParent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String pSummary) {
		summary = pSummary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String pContent) {
		content = pContent;
	}

	public short getOrder() {
		return order;
	}

	public void setOrder(short pOrder) {
		order = pOrder;
	}


	public Long getArea() {
		return area;
	}


	public void setArea(Long pArea) {
		area = pArea;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long pParent) {
		parent = pParent;
	}
}