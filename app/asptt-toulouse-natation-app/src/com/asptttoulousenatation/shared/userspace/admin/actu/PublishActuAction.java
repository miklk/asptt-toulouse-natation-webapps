package com.asptttoulousenatation.shared.userspace.admin.actu;

import java.util.Date;

import net.customware.gwt.dispatch.shared.Action;

public class PublishActuAction implements Action<PublishActionResult> {

	private String title;
	private String summary;
	private String content;
	private Date creationDate;
	private Long documentId;
	
	public PublishActuAction() {
	}
	
	public PublishActuAction(String pTitle, String pSummary, String pContent,
			Date pCreationDate, Long pDocumentId) {
		super();
		title = pTitle;
		summary = pSummary;
		content = pContent;
		creationDate = pCreationDate;
		documentId = pDocumentId;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String pTitle) {
		title = pTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String pContent) {
		content = pContent;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date pCreationDate) {
		creationDate = pCreationDate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String pSummary) {
		summary = pSummary;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long pDocumentId) {
		documentId = pDocumentId;
	}
}