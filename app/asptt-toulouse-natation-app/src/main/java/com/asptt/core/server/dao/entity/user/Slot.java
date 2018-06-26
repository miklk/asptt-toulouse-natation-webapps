package com.asptt.core.server.dao.entity.user;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptt.core.server.dao.entity.IEntity;

@Entity
public class Slot implements IEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3432255398381415591L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private int dayofweek;
	
	
	private String timeslot;
	
	
	private String group;
	
	
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
