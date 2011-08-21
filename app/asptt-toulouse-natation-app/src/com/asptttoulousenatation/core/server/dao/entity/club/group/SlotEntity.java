package com.asptttoulousenatation.core.server.dao.entity.club.group;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SlotEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9049127194178804074L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String dayOfWeek;
	
	@Persistent
	private int begin;
	
	@Persistent
	private int end;
	
	@Persistent
	private Long group;
	
	@Persistent
	private String swimmingPool;
	
	@Persistent
	private String educateur;
	
	public SlotEntity() {
		
	}

	public SlotEntity(Long pId, String pDayOfWeek, int pBegin, int pEnd,
			Long pGroup, String pSwimmingPool, String pEducateur) {
		super();
		id = pId;
		dayOfWeek = pDayOfWeek;
		begin = pBegin;
		end = pEnd;
		group = pGroup;
		swimmingPool = pSwimmingPool;
		educateur = pEducateur;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String pDayOfWeek) {
		dayOfWeek = pDayOfWeek;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int pBegin) {
		begin = pBegin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int pEnd) {
		end = pEnd;
	}

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long pGroup) {
		group = pGroup;
	}

	public String getSwimmingPool() {
		return swimmingPool;
	}

	public void setSwimmingPool(String pSwimmingPool) {
		swimmingPool = pSwimmingPool;
	}

	public String getEducateur() {
		return educateur;
	}

	public void setEducateur(String pEducateur) {
		educateur = pEducateur;
	}
}