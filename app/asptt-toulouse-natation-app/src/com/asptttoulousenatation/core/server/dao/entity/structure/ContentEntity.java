package com.asptttoulousenatation.core.server.dao.entity.structure;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;
import com.google.appengine.api.datastore.Blob;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ContentEntity implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4764437018371593622L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String summary;
	
	@Persistent
	private Blob data;
	
	@Persistent
	private String kind;
	
	@Persistent
	private Long menu;
	
	public ContentEntity() {
		
	}

	public ContentEntity(String pSummary, Blob pData, String pKind, Long pMenu) {
		summary = pSummary;
		data = pData;
		kind = pKind;
		menu = pMenu;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String pSummary) {
		summary = pSummary;
	}

	public Blob getData() {
		return data;
	}

	public void setData(Blob pData) {
		data = pData;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String pKind) {
		kind = pKind;
	}

	public Long getMenu() {
		return menu;
	}

	public void setMenu(Long pMenu) {
		menu = pMenu;
	}
	
}