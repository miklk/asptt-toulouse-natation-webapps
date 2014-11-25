package com.asptttoulousenatation.core.server.dao.entity.swimmer;

import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
public class SwimmerStatEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String DAY_FORMAT = "dd/MM/yyyy";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Persistent
	private Long day;
	
	@Persistent
	private Long swimmer;
	
	@Persistent
	private String daytime;
	
	@Persistent
	private int distance;
	
	@Persistent
	private String comment;
	
	@Persistent
	private Boolean presence;
	
	public SwimmerStatEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public Long getDay() {
		return day;
	}

	public void setDay(Long pDay) {
		day = pDay;
	}

	public Long getSwimmer() {
		return swimmer;
	}

	public void setSwimmer(Long pSwimmer) {
		swimmer = pSwimmer;
	}

	public String getDaytime() {
		return daytime;
	}

	public void setDaytime(String pDaytime) {
		daytime = pDaytime;
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

	public Boolean getPresence() {
		return presence;
	}

	public void setPresence(Boolean pPresence) {
		presence = pPresence;
	}
}