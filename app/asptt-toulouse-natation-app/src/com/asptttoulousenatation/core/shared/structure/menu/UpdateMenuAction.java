package com.asptttoulousenatation.core.shared.structure.menu;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateMenuAction implements Action<UpdateMenuResult> {

	private Long menu;
	private String menuKey;
	private String title;
	private Long contentId;
	private String summary;
	private byte[] content;
	private boolean divider;
	private boolean alone;
	private int order;
	private String identifier;
	
	public UpdateMenuAction() {
		
	}

	public UpdateMenuAction(Long pMenu, String pMenuKey, String pTitle, Long pContentId,
			String pSummary, byte[] pContent, boolean pDivider, boolean pAlone, int pOrder, String pIdentifier) {
		super();
		menu = pMenu;
		menuKey = pMenuKey;
		title = pTitle;
		contentId = pContentId;
		summary = pSummary;
		content = pContent;
		divider = pDivider;
		alone = pAlone;
		order = pOrder;
		identifier = pIdentifier;
	}

	public Long getMenu() {
		return menu;
	}

	public void setMenu(Long pMenu) {
		menu = pMenu;
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

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] pContent) {
		content = pContent;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long pContentId) {
		contentId = pContentId;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int pOrder) {
		order = pOrder;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String pIdentifier) {
		identifier = pIdentifier;
	}
}