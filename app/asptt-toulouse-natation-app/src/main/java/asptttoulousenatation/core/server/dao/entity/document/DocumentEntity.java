package com.asptttoulousenatation.core.server.dao.entity.document;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
public class DocumentEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4843695876503479818L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String summary;
	
	private String mimeType;
	
	private String fileName;
	
	private Date creationDate;
	
	private Long dataId;
	
	private Long menu;
	
	public DocumentEntity() {
		
	}

	public DocumentEntity(String pTitle, String pSummary,
			String pMimeType, String pFileName, Date pCreationDate, Long pDataId, Long pMenu) {
		super();
		title = pTitle;
		summary = pSummary;
		mimeType = pMimeType;
		fileName = pFileName;
		creationDate = pCreationDate;
		dataId = pDataId;
		menu = pMenu;
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

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long pDataId) {
		dataId = pDataId;
	}

	public Long getMenuId() {
		return menu;
	}

	public void setMenuId(Long pMenuId) {
		menu = pMenuId;
	}
}