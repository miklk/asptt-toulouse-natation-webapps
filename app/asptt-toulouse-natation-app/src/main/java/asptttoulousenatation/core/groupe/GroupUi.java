package com.asptttoulousenatation.core.groupe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GroupUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private boolean licenceFfn;
	private boolean inscription;
	private List<SlotUi> slots;
	private int tarif;
	private int tarif2;
	private int tarif3;
	private int tarif4;
	private int tarifWeight;
	private int tarifUnique;
	private int tarifUnique2;
	private int tarifUnique3;
	private int tarifUnique4;
	private boolean seanceunique;
	private boolean nouveau;
	private String description;
	private boolean enf;
	private boolean competition;
	private boolean secondes;
	private int capacite;
	private int occupe;
	
	public GroupUi() {
		super();
		slots = new ArrayList<SlotUi>(0);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long pId) {
		id = pId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public List<SlotUi> getSlots() {
		return slots;
	}

	public void setSlots(List<SlotUi> pSlots) {
		slots = pSlots;
	}

	public void addSlot(SlotUi pSlot) {
		slots.add(pSlot);
	}
	
	public void addSlots(Collection<SlotUi> pSlots) {
		slots.addAll(pSlots);
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

	public boolean isEnf() {
		return enf;
	}

	public void setEnf(boolean pEnf) {
		enf = pEnf;
	}

	public boolean isCompetition() {
		return competition;
	}

	public void setCompetition(boolean competition) {
		this.competition = competition;
	}

	public boolean isSecondes() {
		return secondes;
	}

	public void setSecondes(boolean secondes) {
		this.secondes = secondes;
	}

	public int getTarifUnique() {
		return tarifUnique;
	}

	public void setTarifUnique(int tarifUnique) {
		this.tarifUnique = tarifUnique;
	}

	public int getTarifUnique2() {
		return tarifUnique2;
	}

	public void setTarifUnique2(int tarifUnique2) {
		this.tarifUnique2 = tarifUnique2;
	}

	public int getTarifUnique3() {
		return tarifUnique3;
	}

	public void setTarifUnique3(int tarifUnique3) {
		this.tarifUnique3 = tarifUnique3;
	}

	public int getTarifUnique4() {
		return tarifUnique4;
	}

	public void setTarifUnique4(int tarifUnique4) {
		this.tarifUnique4 = tarifUnique4;
	}

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public int getOccupe() {
		return occupe;
	}

	public void setOccupe(int occupe) {
		this.occupe = occupe;
	}
	
}