package com.asptttoulousenatation.core.swimmer.presence;

import java.io.Serializable;
import java.util.List;

import org.joda.time.DateTime;

public class PresenceValidateParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idNageur;
	private DateTime begin;
	private List<Boolean[]> presences;
	public Long getIdNageur() {
		return idNageur;
	}
	public void setIdNageur(Long idNageur) {
		this.idNageur = idNageur;
	}
	public List<Boolean[]> getPresences() {
		return presences;
	}
	public void setPresences(List<Boolean[]> presences) {
		this.presences = presences;
	}
	public DateTime getBegin() {
		return begin;
	}
	public void setBegin(DateTime begin) {
		this.begin = begin;
	}
	
}
