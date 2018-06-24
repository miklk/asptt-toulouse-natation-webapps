package com.asptttoulousenatation.core.server.dao.entity.structure;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;
import com.google.appengine.api.datastore.Blob;

@Entity
public class ContentEntity implements IEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4764437018371593622L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String summary;
	
	private Blob data;
	
	private String kind;
	
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