package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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