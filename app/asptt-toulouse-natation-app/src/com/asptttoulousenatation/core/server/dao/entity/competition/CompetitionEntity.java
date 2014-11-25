package com.asptttoulousenatation.core.server.dao.entity.competition;

import java.util.Date;
import java.util.Set;

import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
public class CompetitionEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9157147920816352745L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Persistent
	private String title;
	
	@Persistent
	private String place;
	
	@Persistent
	private Date begin;
	
	@Persistent
	private Date end;
	
	@Persistent
	private String saison;
	
	@Persistent
	private Set<Long> days;
	
	public CompetitionEntity() {
		
	}

	public CompetitionEntity(Long pId, String pTitle, String pPlace,
			Date pBegin, Date pEnd, String pSaison, Set<Long> pDays) {
		super();
		id = pId;
		title = pTitle;
		place = pPlace;
		begin = pBegin;
		end = pEnd;
		saison = pSaison;
		days = pDays;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String pPlace) {
		place = pPlace;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date pBegin) {
		begin = pBegin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date pEnd) {
		end = pEnd;
	}

	public String getSaison() {
		return saison;
	}

	public void setSaison(String pSaison) {
		saison = pSaison;
	}

	public Set<Long> getDays() {
		return days;
	}

	public void setDays(Set<Long> pDays) {
		days = pDays;
	}

}