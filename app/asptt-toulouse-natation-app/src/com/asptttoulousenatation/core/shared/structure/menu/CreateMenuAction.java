package com.asptttoulousenatation.core.shared.structure.menu;

import net.customware.gwt.dispatch.shared.Action;

public class CreateMenuAction implements Action<CreateMenuResult> {

	private String menuKey;
	private String title;
	private String summary;
	private String content;
	private int order;
	private Long area;
	private Long parent;
	private boolean divider;
	private boolean alone;
	private String identifier;
	
	public CreateMenuAction() {
		
	}

	public CreateMenuAction(String pMenuKey, String pTitle, String pSummary, String pContent,
			int pOrder, Long pArea, Long pParent, boolean pDivider, boolean pAlone, String pIdentifier) {
		super();
		menuKey = pMenuKey;
		title = pTitle;
		summary = pSummary;
		content = pContent;
		order = pOrder;
		area = pArea;
		parent = pParent;
		divider = pDivider;
		alone = pAlone;
		identifier = pIdentifier;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String pMenuKey) {
		menuKey = pMenuKey;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int pOrder) {
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

	public boolean isDivider() {
		return divider;
	}

	public void setDivider(boolean pDivider) {
		divider = pDivider;
	}

	public boolean isAlone() {
		return alone;
	}

	public void setAlone(boolean pAlone) {
		alone = pAlone;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String pIdentifier) {
		identifier = pIdentifier;
	}
	
}