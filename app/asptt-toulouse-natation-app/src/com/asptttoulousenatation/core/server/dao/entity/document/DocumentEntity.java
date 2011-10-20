package com.asptttoulousenatation.core.server.dao.entity.document;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class DocumentEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4843695876503479818L;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String title;
	
	@Persistent
	private String summary;
	
	@Persistent
	private String mimeType;
	
	@Persistent
	private String fileName;
	
	@Persistent
	private Date creationDate;
	
	@Persistent
	private Long dataId;
	
	@Persistent
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