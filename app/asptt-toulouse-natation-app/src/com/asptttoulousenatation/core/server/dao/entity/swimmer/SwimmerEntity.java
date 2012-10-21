package com.asptttoulousenatation.core.server.dao.entity.swimmer;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SwimmerEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
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