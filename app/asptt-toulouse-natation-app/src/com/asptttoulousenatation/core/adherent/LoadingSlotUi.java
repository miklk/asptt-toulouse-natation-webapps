package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.asptttoulousenatation.core.groupe.SlotUi;

public class LoadingSlotUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5537386798596241529L;
	private String piscine;
	private List<SlotUi> lundi;
	private List<SlotUi> mardi;
	private List<SlotUi> mercredi;
	private List<SlotUi> jeudi;
	private List<SlotUi> vendredi;
	private List<SlotUi> samedi;
	
	public LoadingSlotUi() {
		lundi = new ArrayList<SlotUi>(3);
		mardi = new ArrayList<SlotUi>(3);
		mercredi = new ArrayList<SlotUi>(3);
		jeudi = new ArrayList<SlotUi>(3);
		vendredi = new ArrayList<SlotUi>(3);
		samedi = new ArrayList<SlotUi>(3);
	}
	
	public LoadingSlotUi(String pPiscine) {
		this();
		piscine = pPiscine;
	}
	
	public void sort() {
		Comparator<SlotUi> sortByBeginHour = new Comparator<SlotUi>() {
			
			@Override
			public int compare(SlotUi o1, SlotUi o2) {
				return (new CompareToBuilder()).append(o1.getBegin(), o2.getBegin()).toComparison();
			}
		};
		Collections.sort(lundi, sortByBeginHour);
		Collections.sort(mardi, sortByBeginHour);
		Collections.sort(mercredi, sortByBeginHour);
		Collections.sort(jeudi, sortByBeginHour);
		Collections.sort(vendredi, sortByBeginHour);
		Collections.sort(samedi, sortByBeginHour);
	}
	
	public String getPiscine() {
		return piscine;
	}

	public void setPiscine(String pPiscine) {
		piscine = pPiscine;
	}

	public List<SlotUi> getLundi() {
		return lundi;
	}

	public void setLundi(List<SlotUi> pLundi) {
		lundi = pLundi;
	}

	public List<SlotUi> getMardi() {
		return mardi;
	}

	public void setMardi(List<SlotUi> pMardi) {
		mardi = pMardi;
	}

	public List<SlotUi> getMercredi() {
		return mercredi;
	}

	public void setMercredi(List<SlotUi> pMercredi) {
		mercredi = pMercredi;
	}

	public List<SlotUi> getJeudi() {
		return jeudi;
	}

	public void setJeudi(List<SlotUi> pJeudi) {
		jeudi = pJeudi;
	}

	public List<SlotUi> getVendredi() {
		return vendredi;
	}

	public void setVendredi(List<SlotUi> pVendredi) {
		vendredi = pVendredi;
	}

	public List<SlotUi> getSamedi() {
		return samedi;
	}

	public void setSamedi(List<SlotUi> pSamedi) {
		samedi = pSamedi;
	}
}