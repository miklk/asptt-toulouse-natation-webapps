package com.asptttoulousenatation.shared.userspace.admin.structure.content;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateContentAction implements Action<UpdateContentResult> {

	private Long contentId;
	private String summary;
	private byte[] content;
	
	public UpdateContentAction() {
		
	}

	public UpdateContentAction(Long pContentId, String pSummary, byte[] pContent) {
		contentId = pContentId;
		summary = pSummary;
		content = pContent;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long pContentId) {
		contentId = pContentId;
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
