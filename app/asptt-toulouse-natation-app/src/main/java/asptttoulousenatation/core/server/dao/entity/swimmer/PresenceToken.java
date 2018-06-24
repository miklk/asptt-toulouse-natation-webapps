package com.asptttoulousenatation.core.server.dao.entity.swimmer;

import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.Id;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
public class PresenceToken implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	
	private Date begin;
	
	
	private Long swimmer;
	
	
	private Long groupe;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Long getSwimmer() {
		return swimmer;
	}
	public void setSwimmer(Long swimmer) {
		this.swimmer = swimmer;
	}
	public Long getGroupe() {
		return groupe;
	}
	public void setGroupe(Long groupe) {
		this.groupe = groupe;
	}
	
}