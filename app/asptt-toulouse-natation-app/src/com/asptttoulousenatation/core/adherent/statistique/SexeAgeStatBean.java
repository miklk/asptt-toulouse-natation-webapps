package com.asptttoulousenatation.core.adherent.statistique;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SexeAgeStatBean {

	private String sexe;
	private AdherentStatAge age;
	
	public SexeAgeStatBean() {
		age = new AdherentStatAge();
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String pSexe) {
		sexe = pSexe;
	}
	public AdherentStatAge getAge() {
		return age;
	}
	public void setAge(AdherentStatAge pAge) {
		age = pAge;
	}
	
	public void addMineur() {
		age.setMineur(age.getMineur() + 1);
	}
	
	public void addMajeur() {
		age.setMajeur(age.getMajeur() + 1);
	}
}