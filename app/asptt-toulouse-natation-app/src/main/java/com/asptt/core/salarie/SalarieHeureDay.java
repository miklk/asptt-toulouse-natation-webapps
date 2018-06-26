package com.asptt.core.salarie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.asptt.core.server.dao.entity.salarie.SalarieHeureEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

public class SalarieHeureDay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonSerialize(using=DateSerializer.class)
	private Date day;
	private List<SalarieHeureEntity> heures;
	
	public SalarieHeureDay() {
		heures = new ArrayList<>();
	}
	
	public void addHeure(SalarieHeureEntity heure) {
		heures.add(heure);
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public List<SalarieHeureEntity> getHeures() {
		return heures;
	}
	public void setHeures(List<SalarieHeureEntity> heures) {
		this.heures = heures;
	}
	
}
