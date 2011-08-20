package com.asptttoulousenatation.core.server.dao.entity.competition;

import java.util.Date;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class CompetitionDayEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8396666441854399599L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
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