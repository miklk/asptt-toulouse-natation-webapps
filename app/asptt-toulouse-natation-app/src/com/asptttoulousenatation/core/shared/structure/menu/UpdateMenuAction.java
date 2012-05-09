package com.asptttoulousenatation.core.shared.structure.menu;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateMenuAction implements Action<UpdateMenuResult> {

	private Long menu;
	private String menuKey;
	private String title;
	private Long contentId;
	private String summary;
	private byte[] content;
	
	public UpdateMenuAction() {
		
	}

	public UpdateMenuAction(Long pMenu, String pMenuKey, String pTitle, Long pContentId,
			String pSummary, byte[] pContent) {
		super();
		menu = pMenu;
		menuKey = pMenuKey;
		title = pTitle;
		contentId = pContentId;
		summary = pSummary;
		content = pContent;
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
	
}