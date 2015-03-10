package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;

public class GetAdherentsStatResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3404097203132797278L;
	private List<InscriptionEntity2> adherents;
	
	public GetAdherentsStatResult() {
		adherents = Collections.emptyList();
	}

	public List<InscriptionEntity2> getAdherents() {
		return adherents;
	}

	public void setAdherents(List<InscriptionEntity2> pAdherents) {
		adherents = pAdherents;
	}
}
