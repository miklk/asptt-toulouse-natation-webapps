package com.asptttoulousenatation.core.shared.club.group;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateGroupAction implements Action<UpdateGroupResult> {

	private Long id;
	private String title;
	private boolean licenceFfn;
	private boolean inscription;
	private int tarif;
	private int tarif2;
	private int tarif3;
	private int tarif4;
	private boolean seanceunique;
	private int tarifWeight;
	private boolean nouveau;
	private String description;
	
	public UpdateGroupAction() {
	}
	

	public UpdateGroupAction(Long pId, String pTitle, boolean pLicenceFfn, boolean pInscription, int pTarif, int pTarif2, int pTarif3, int pTarif4, boolean pSeanceunique, int pTarifWeight, boolean pNouveau, String pDescription) {
		super();
		id = pId;
		title = pTitle;
		licenceFfn = pLicenceFfn;
		inscription = pInscription;
		tarif = pTarif;
		tarif2 = pTarif2;
		tarif3 = pTarif3;
		tarif4 = pTarif4;
		seanceunique = pSeanceunique;
		tarifWeight = pTarifWeight;
		nouveau = pNouveau;
		description = pDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public boolean isLicenceFfn() {
		return licenceFfn;
	}


	public void setLicenceFfn(boolean pLicenceFfn) {
		licenceFfn = pLicenceFfn;
	}


	public boolean isInscription() {
		return inscription;
	}


	public void setInscription(boolean pInscription) {
		inscription = pInscription;
	}


	public int getTarif() {
		return tarif;
	}


	public void setTarif(int pTarif) {
		tarif = pTarif;
	}


	public int getTarif2() {
		return tarif2;
	}


	public void setTarif2(int pTarif2) {
		tarif2 = pTarif2;
	}


	public int getTarif3() {
		return tarif3;
	}


	public void setTarif3(int pTarif3) {
		tarif3 = pTarif3;
	}


	public int getTarif4() {
		return tarif4;
	}


	public void setTarif4(int pTarif4) {
		tarif4 = pTarif4;
	}


	public boolean isSeanceunique() {
		return seanceunique;
	}


	public void setSeanceunique(boolean pSeanceunique) {
		seanceunique = pSeanceunique;
	}


	public int getTarifWeight() {
		return tarifWeight;
	}


	public void setTarifWeight(int pTarifWeight) {
		tarifWeight = pTarifWeight;
	}


	public boolean isNouveau() {
		return nouveau;
	}


	public void setNouveau(boolean pNouveau) {
		nouveau = pNouveau;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String pDescription) {
		description = pDescription;
	}
}