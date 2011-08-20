package com.asptttoulousenatation.shared.userspace.admin.structure.content;

import net.customware.gwt.dispatch.shared.Action;

public class CreateContentAction implements Action<CreateContentResult> {

	private Long menuId;
	private String summary;
	private byte[] content;
	
	public CreateContentAction() {
		
	}

	public CreateContentAction(Long pMenuId, String pSummary, byte[] pContent) {
		menuId = pMenuId;
		summary = pSummary;
		content = pContent;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long pMenuId) {
		menuId = pMenuId;
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
	
}
