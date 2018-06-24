package com.asptttoulousenatation.core.adherent.statistique;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AdherentStatAge {

	private int mineur;
	private int majeur;
	
	public void addMineur() {
		mineur++;
	}
	
	public void addMajeur() {
		majeur++;
	}
	
	public int getMineur() {
		return mineur;
	}
	public void setMineur(int pMineur) {
		mineur = pMineur;
	}
	public int getMajeur() {
		return majeur;
	}
	public void setMajeur(int pMajeur) {
		majeur = pMajeur;
	}
}
