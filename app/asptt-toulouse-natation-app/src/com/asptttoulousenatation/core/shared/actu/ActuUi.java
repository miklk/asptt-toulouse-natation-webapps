package com.asptttoulousenatation.core.shared.actu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ActuUi implements IsSerializable {
	private Long id;
	private String title;
	private String summary;
	private Date creationDate;
	private String content;
	
	private List<DocumentUi> documentSet;

	public ActuUi() {
		documentSet = new ArrayList<DocumentUi>(0);
	}
	
	public ActuUi(Long pId,String pTitle, String pSummary, Date pCreationDate,
			String pContent) {
		this();
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

	public List<DocumentUi> getDocumentSet() {
		return documentSet;
	}

	public void setDocumentSet(List<DocumentUi> pDocumentSet) {
		documentSet = pDocumentSet;
	}
	
}