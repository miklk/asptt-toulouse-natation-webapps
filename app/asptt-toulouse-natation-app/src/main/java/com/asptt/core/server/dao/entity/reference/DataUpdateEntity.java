package com.asptt.core.server.dao.entity.reference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptt.core.server.dao.entity.IEntity;

@Entity
public class DataUpdateEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3182065752809718677L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String kind;
	
	private Boolean updated;
	
	public DataUpdateEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String pKind) {
		kind = pKind;
	}

	public Boolean getUpdated() {
		return updated;
	}

	public void setUpdated(Boolean pUpdated) {
		updated = pUpdated;
	}
}