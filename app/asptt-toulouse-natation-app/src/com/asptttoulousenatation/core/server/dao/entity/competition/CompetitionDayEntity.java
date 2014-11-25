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
public class CompetitionDayEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8396666441854399599L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Persistent
	private int day;
	
	@Persistent
	private Date begin;
	
	@Persistent
	private Date end;

	@Persistent
	private int needed;
	
	@Persistent
	private Set<Long> officiels;
	
	public CompetitionDayEntity() {
		
	}

	public CompetitionDayEntity(Long pId, int pDay, Date pBegin, Date pEnd,
			int pNeeded, Set<Long> pOfficiels) {
		super();
		id = pId;
		day = pDay;
		begin = pBegin;
		end = pEnd;
		needed = pNeeded;
		officiels = pOfficiels;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int pDay) {
		day = pDay;
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

	public int getNeeded() {
		return needed;
	}

	public void setNeeded(int pNeeded) {
		needed = pNeeded;
	}

	public Set<Long> getOfficiels() {
		return officiels;
	}

	public void setOfficiels(Set<Long> pOfficiels) {
		officiels = pOfficiels;
	}
}