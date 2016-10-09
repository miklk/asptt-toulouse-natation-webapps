package com.asptttoulousenatation.core.swimmer.presence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class PresenceTokenResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DateTime begin;
	private DateTime end;
	private Long swimmer;
	private String nom;
	private String prenom;
	private List<Boolean[]> presences;
	
	public PresenceTokenResult() {
		presences = new ArrayList<>(7);
		presences.add(new Boolean[]{false, false, false, false});
		presences.add(new Boolean[]{false, false, false, false});
		presences.add(new Boolean[]{false, false, false, false});
		presences.add(new Boolean[]{false, false, false, false});
		presences.add(new Boolean[]{false, false, false, false});
		presences.add(new Boolean[]{false, false, false, false});
		presences.add(new Boolean[]{false, false, false, false});
	}
	
	public void addJour(int jour, int moment, Boolean presence) {
		presences.get(jour)[moment] = presence;
	}

	public DateTime getBegin() {
		return begin;
	}

	public void setBegin(DateTime begin) {
		this.begin = begin;
	}

	public DateTime getEnd() {
		return end;
	}

	public void setEnd(DateTime end) {
		this.end = end;
	}

	public Long getSwimmer() {
		return swimmer;
	}

	public void setSwimmer(Long swimmer) {
		this.swimmer = swimmer;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<Boolean[]> getPresences() {
		return presences;
	}

	public void setPresences(List<Boolean[]> presences) {
		this.presences = presences;
	}
}