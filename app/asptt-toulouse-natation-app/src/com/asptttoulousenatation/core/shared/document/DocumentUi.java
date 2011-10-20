package com.asptttoulousenatation.core.shared.document;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DocumentUi implements IsSerializable {

	private Long id;

	private String title;

	private String summary;

	private String mimeType;

	private String fileName;
	
	private Date creationDate;

	private Long data;

	public DocumentUi() {

	}

	public DocumentUi(Long pId, String pTitle, String pSummary,
			String pMimeType, String pFileName, Date pCreationDate, Long pData) {
		super();
		id = pId;
		title = pTitle;
		summary = pSummary;
		mimeType = pMimeType;
		fileName = pFileName;
		creationDate = pCreationDate;
		data = pData;
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

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String pMimeType) {
		mimeType = pMimeType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String pFileName) {
		fileName = pFileName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date pCreationDate) {
		creationDate = pCreationDate;
	}

	public Long getData() {
		return data;
	}

	public void setData(Long pData) {
		data = pData;
	}
}