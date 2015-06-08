package com.asptttoulousenatation.core.groupe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupUi implements IsSerializable {

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
	private boolean seanceunique;
	private boolean nouveau;
	private String description;
	private boolean enf;
	
	public GroupUi() {
		super();
		slots = new ArrayList<SlotUi>(0);
	}
	
	public GroupUi(Long pId, String pTitle, boolean pLicenceFfn, boolean pInscription, int pTarif, int pTarif2, int pTarif3, int pTarif4, boolean pSeanceunique, int pTarifWeight, boolean pNouveau, String pDescription, boolean pEnf) {
		super();
		id = pId;
		title = pTitle;
		licenceFfn = pLicenceFfn;
		inscription = pInscription;
		tarif = pTarif;
		tarif2 = pTarif2;
		tarif3 = pTarif3;
		tarif4 = pTarif4;
		tarifWeight = pTarifWeight;
		seanceunique = pSeanceunique;
		nouveau = pNouveau;
		description = pDescription;
		enf = pEnf;
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
}