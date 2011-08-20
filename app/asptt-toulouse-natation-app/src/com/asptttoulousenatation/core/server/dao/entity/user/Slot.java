package com.asptttoulousenatation.core.server.dao.entity.user;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Slot implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3432255398381415591L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private int dayofweek;
	
	@Persistent
	private String timeslot;
	
	@Persistent
	private String group;
	
	@Persistent
	private String swimmingpool;
	
	public Slot() {
		
	}

	public Slot(Long pId, int pDayofweek, String pTimeslot, String pGroup,
			String pSwimmingpool) {
		super();
		id = pId;
		dayofweek = pDayofweek;
		timeslot = pTimeslot;
		group = pGroup;
		swimmingpool = pSwimmingpool;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public int getDayofweek() {
		return dayofweek;
	}

	public void setDayofweek(int pDayofweek) {
		dayofweek = pDayofweek;
	}

	public String getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(String pTimeslot) {
		timeslot = pTimeslot;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String pGroup) {
		group = pGroup;
	}

	public String getSwimmingpool() {
		return swimmingpool;
	}

	public void setSwimmingpool(String pSwimmingpool) {
		swimmingpool = pSwimmingpool;
	}
}
