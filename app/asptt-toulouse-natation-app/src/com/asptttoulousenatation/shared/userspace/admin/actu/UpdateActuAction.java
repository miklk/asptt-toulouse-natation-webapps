package com.asptttoulousenatation.shared.userspace.admin.actu;

import java.util.Date;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateActuAction implements Action<UpdateActuResult> {

	private Long id;
	private String title;
	private String summary;
	private Date creationDate;
	private String content;
	private String imageUrl;
	private boolean competition;
	
	public UpdateActuAction() {
		
	}
	
	public UpdateActuAction(Long pId, String pTitle,
			Date pCreationDate, String pSummary, String pContent, String pImageUrl, boolean pCompetition) {
		id = pId;
		title = pTitle;
		creationDate = pCreationDate;
		summary = pSummary;
		content = pContent;
		imageUrl = pImageUrl;
		competition = pCompetition;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String pImageUrl) {
		imageUrl = pImageUrl;
	}

	public boolean isCompetition() {
		return competition;
	}

	public void setCompetition(boolean pCompetition) {
		competition = pCompetition;
	}
	
}