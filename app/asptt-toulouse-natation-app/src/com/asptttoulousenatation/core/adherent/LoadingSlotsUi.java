package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class LoadingSlotsUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4011854874449401906L;
	private List<LoadingSlotUi> slots;
	private boolean seconde;
	
	public LoadingSlotsUi() {
		slots = new ArrayList<LoadingSlotUi>();
		seconde = false;
	}
	
	public void addSlots(LoadingSlotUi pSlots) {
		slots.add(pSlots);
	}
	
	public void sort() {
		Collections.sort(slots, new Comparator<LoadingSlotUi>() {

			@Override
			public int compare(LoadingSlotUi o1, LoadingSlotUi o2) {
				return new CompareToBuilder().append(o1.getPiscine(), o2.getPiscine()).toComparison();
			}
		});
		for(LoadingSlotUi loadingSlotUi: slots) {
			loadingSlotUi.sort();
		}
	}
	
	public List<LoadingSlotUi> getSlots() {
		return slots;
	}

	public void setSlots(List<LoadingSlotUi> pSlots) {
		slots = pSlots;
	}

	public boolean isSeconde() {
		return seconde;
	}

	public void setSeconde(boolean pSeconde) {
		seconde = pSeconde;
	}
}