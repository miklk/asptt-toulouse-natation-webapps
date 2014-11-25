package com.asptttoulousenatation.core.shared.actu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.google.gwt.user.client.rpc.IsSerializable;
@XmlRootElement
public class ActuUi implements IsSerializable {
	private Long id;
	private String title;
	private String summary;
	private Date creationDate;
	private String content;
	private String imageUrl;
	private boolean competition;
	private Date expiration;
	
	private List<DocumentUi> documentSet;
	
	private boolean hasDocument;

	public ActuUi() {
		documentSet = new ArrayList<DocumentUi>(0);
		hasDocument = false;
	}
	
	public ActuUi(Long pId,String pTitle, String pSummary, Date pCreationDate,
			String pContent, String pImageUrl, boolean pCompetition, Date pExpiration) {
		this();
		id = pId;
		title = pTitle;
		summary = pSummary;
		creationDate = pCreationDate;
		content = pContent;
		imageUrl = pImageUrl;
		competition = pCompetition;
		expiration = pExpiration;
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
		hasDocument = documentSet != null || !documentSet.isEmpty();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String pImageUrl) {
		imageUrl = pImageUrl;
	}

	public boolean isHasDocument() {
		return hasDocument;
	}

	public void setHasDocument(boolean pHasDocument) {
		hasDocument = pHasDocument;
	}

	public boolean isCompetition() {
		return competition;
	}

	public void setCompetition(boolean pCompetition) {
		competition = pCompetition;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date pExpiration) {
		expiration = pExpiration;
	}
}