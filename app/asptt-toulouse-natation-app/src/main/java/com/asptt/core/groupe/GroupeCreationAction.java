package com.asptt.core.groupe;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GroupeCreationAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String intitule;
	private String description;
	private boolean ffn;
	private boolean inscription;
	private boolean seanceUnique;
	private boolean nouveau;
	private boolean enf;
	private int tarifPriorite;
	private int tarif1;
	private int tarif2;
	private int tarif3;
	private int tarif4;
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isFfn() {
		return ffn;
	}
	public void setFfn(boolean ffn) {
		this.ffn = ffn;
	}
	public boolean isInscription() {
		return inscription;
	}
	public void setInscription(boolean inscription) {
		this.inscription = inscription;
	}
	public boolean isSeanceUnique() {
		return seanceUnique;
	}
	public void setSeanceUnique(boolean seanceUnique) {
		this.seanceUnique = seanceUnique;
	}
	public boolean isNouveau() {
		return nouveau;
	}
	public void setNouveau(boolean nouveau) {
		this.nouveau = nouveau;
	}
	public boolean isEnf() {
		return enf;
	}
	public void setEnf(boolean enf) {
		this.enf = enf;
	}
	public int getTarifPriorite() {
		return tarifPriorite;
	}
	public void setTarifPriorite(int tarifPriorite) {
		this.tarifPriorite = tarifPriorite;
	}
	public int getTarif1() {
		return tarif1;
	}
	public void setTarif1(int tarif1) {
		this.tarif1 = tarif1;
	}
	public int getTarif2() {
		return tarif2;
	}
	public void setTarif2(int tarif2) {
		this.tarif2 = tarif2;
	}
	public int getTarif3() {
		return tarif3;
	}
	public void setTarif3(int tarif3) {
		this.tarif3 = tarif3;
	}
	public int getTarif4() {
		return tarif4;
	}
	public void setTarif4(int tarif4) {
		this.tarif4 = tarif4;
	}
	
	
}
