package com.asptttoulousenatation.core.server.dao.entity.swimmer;

import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
public class SwimmerEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Persistent
	private boolean stat;
	
	@Persistent
	private Long group;
	
	@Persistent
	private Long user;
	
	public SwimmerEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public boolean isStat() {
		return stat;
	}

	public void setStat(boolean pStat) {
		stat = pStat;
	}

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long pGroup) {
		group = pGroup;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long pUser) {
		user = pUser;
	}
}