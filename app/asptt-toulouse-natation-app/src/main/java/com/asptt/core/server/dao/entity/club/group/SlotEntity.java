package com.asptt.core.server.dao.entity.club.group;

import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.asptt.core.server.dao.entity.IEntity;

@Entity
public class SlotEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9049127194178804074L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String dayOfWeek;
	
	
	private int begin;
	
	
	private int end;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDt;
	
	
	private Long group;
	
	
	private String swimmingPool;
	
	
	private String educateur;
	
	
	private Integer placeDisponible;
	
	
	private Integer placeRestante;
	
	
	private Boolean second;
	
	
	private Boolean hasSecond;
	
	public SlotEntity() {
		
	}

	public SlotEntity(Long pId, String pDayOfWeek, int pBegin, int pEnd,
			Long pGroup, String pSwimmingPool, String pEducateur,
			Integer pPlaceDisponible, Integer pPlaceRestante, Boolean pSecond, Boolean pHasSecond) {
		super();
		id = pId;
		dayOfWeek = pDayOfWeek;
		begin = pBegin;
		end = pEnd;
		group = pGroup;
		swimmingPool = pSwimmingPool;
		educateur = pEducateur;
		placeDisponible = pPlaceDisponible;
		placeRestante = pPlaceRestante;
		second = pSecond;
		hasSecond = pHasSecond;
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

	public Integer getPlaceDisponible() {
		return placeDisponible;
	}

	public void setPlaceDisponible(Integer pPlaceDisponible) {
		placeDisponible = pPlaceDisponible;
	}

	public Integer getPlaceRestante() {
		return placeRestante;
	}

	public void setPlaceRestante(Integer pPlaceRestante) {
		placeRestante = pPlaceRestante;
	}

	public Boolean getSecond() {
		return second;
	}

	public void setSecond(Boolean pSecond) {
		second = pSecond;
	}

	public Date getBeginDt() {
		return beginDt;
	}

	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public Boolean getHasSecond() {
		return hasSecond;
	}

	public void setHasSecond(Boolean hasSecond) {
		this.hasSecond = hasSecond;
	}
}