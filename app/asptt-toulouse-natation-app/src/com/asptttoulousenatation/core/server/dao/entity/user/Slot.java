package com.asptttoulousenatation.core.server.dao.entity.user;

import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
public class Slot implements IEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3432255398381415591L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
