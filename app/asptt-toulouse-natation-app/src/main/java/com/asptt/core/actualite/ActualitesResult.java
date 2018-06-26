package com.asptt.core.actualite;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptt.core.shared.actu.ActuUi;

@XmlRootElement
public class ActualitesResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2842607720229890729L;
	private List<ActuUi> actualites;
	
	public ActualitesResult() {
		
	}

	public List<ActuUi> getActualites() {
		return actualites;
	}

	public void setActualites(List<ActuUi> pActualites) {
		actualites = pActualites;
	}
}
