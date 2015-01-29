package com.asptttoulousenatation.core.swimmer;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class SwimmerStatUi {

	private Long id;
	private Long adherent;
	private String nom;
	private String prenom;
	private Long day;
	private boolean selected;
	
	private SwimmerStatDataUi morning;
	private SwimmerStatDataUi midday;
	private SwimmerStatDataUi night;
	private SwimmerStatDataUi bodybuilding;
	private SwimmerStatDataUi presence;
	
	private String comment;
	
	public SwimmerStatUi() {
		morning = new SwimmerStatDataUi();
		midday = new SwimmerStatDataUi();
		night = new SwimmerStatDataUi();
		bodybuilding = new SwimmerStatDataUi();
		presence = new SwimmerStatDataUi();
		selected = false;
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long pId) {
		id = pId;
	}

	public Long getAdherent() {
		return adherent;
	}


	public void setAdherent(Long pAdherent) {
		adherent = pAdherent;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String pNom) {
		nom = pNom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String pPrenom) {
		prenom = pPrenom;
	}


	public SwimmerStatDataUi getMorning() {
		return morning;
	}


	public void setMorning(SwimmerStatDataUi pMorning) {
		morning = pMorning;
	}


	public SwimmerStatDataUi getMidday() {
		return midday;
	}


	public void setMidday(SwimmerStatDataUi pMidday) {
		midday = pMidday;
	}


	public SwimmerStatDataUi getNight() {
		return night;
	}


	public void setNight(SwimmerStatDataUi pNight) {
		night = pNight;
	}


	public SwimmerStatDataUi getBodybuilding() {
		return bodybuilding;
	}


	public void setBodybuilding(SwimmerStatDataUi pBodybuilding) {
		bodybuilding = pBodybuilding;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String pComment) {
		comment = pComment;
	}

	public Long getDay() {
		return day;
	}

	public void setDay(Long pDay) {
		day = pDay;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean pSelected) {
		selected = pSelected;
	}


	public SwimmerStatDataUi getPresence() {
		return presence;
	}


	public void setPresence(SwimmerStatDataUi pPresence) {
		presence = pPresence;
	}
	
}