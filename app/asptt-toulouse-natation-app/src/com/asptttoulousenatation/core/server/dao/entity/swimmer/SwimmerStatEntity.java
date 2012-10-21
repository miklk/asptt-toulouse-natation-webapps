package com.asptttoulousenatation.core.server.dao.entity.swimmer;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SwimmerStatEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String DAY_FORMAT = "dd/MM/yyyy";

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String day;
	
	@Persistent
	private Long swimmer;
	
	@Persistent
	private String dayTime;
	
	@Persistent
	private int distance;
	
	@Persistent
	private String comment;
	
	public SwimmerStatEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String pDay) {
		day = pDay;
	}

	public Long getSwimmer() {
		return swimmer;
	}

	public void setSwimmer(Long pSwimmer) {
		swimmer = pSwimmer;
	}

	public String getDayTime() {
		return dayTime;
	}

	public void setDayTime(String pDayTime) {
		dayTime = pDayTime;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int pDistance) {
		distance = pDistance;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String pComment) {
		comment = pComment;
	}
}