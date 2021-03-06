package com.asptttoulousenatation.core.server.dao.entity.competition;

import java.util.Date;
import java.util.Set;


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
	
	
	private String title;
	
	
	private String place;
	
	
	private Date begin;
	
	
	private Date end;
	
	
	private String saison;
	
	
	private Set<Long> days;
	
	private CompetitionKindEnum kind;
	
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

	public CompetitionKindEnum getKind() {
		return kind;
	}

	public void setKind(CompetitionKindEnum kind) {
		this.kind = kind;
	}

}