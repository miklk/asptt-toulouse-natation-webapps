package com.asptttoulousenatation.core.shared.document;


import net.customware.gwt.dispatch.shared.Action;

public class UpdateDocumentAction implements Action<UpdateDocumentResult> {

	private Long id;
	private String title;
	private String summary;
	
	public UpdateDocumentAction() {
		
	}

	public UpdateDocumentAction(Long pId, String pTitle, String pSummary) {
		super();
		id = pId;
		title = pTitle;
		summary = pSummary;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String pSummary) {
		summary = pSummary;
	}
}