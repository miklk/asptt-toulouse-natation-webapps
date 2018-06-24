package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class GetAdherentsResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3404097203132797278L;
	private List<AdherentSummaryBean> adherents;
	
	public GetAdherentsResult() {
		adherents = Collections.emptyList();
	}

	public List<AdherentSummaryBean> getAdherents() {
		return adherents;
	}

	public void setAdherents(List<AdherentSummaryBean> pAdherents) {
		adherents = pAdherents;
	}
}
