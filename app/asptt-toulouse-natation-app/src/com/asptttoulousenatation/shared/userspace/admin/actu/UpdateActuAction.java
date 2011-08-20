package com.asptttoulousenatation.shared.userspace.admin.actu;

import java.util.Date;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateActuAction implements Action<UpdateActuResult> {

	private Long id;
	private String title;
	private String summary;
	private Date creationDate;
	private String content;
	
	public UpdateActuAction() {
		
	}
	
	public UpdateActuAction(Long pId, String pTitle, String pSummary,
			Date pCreationDate, String pContent) {
		id = pId;
		title = pTitle;
		summary = pSummary;
		creationDate = pCreationDate;
		content = pContent;
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
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date pCreationDate) {
		creationDate = pCreationDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String pContent) {
		content = pContent;
	}
}