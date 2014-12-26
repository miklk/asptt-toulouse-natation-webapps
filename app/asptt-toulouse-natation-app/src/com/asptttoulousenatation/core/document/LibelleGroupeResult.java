package com.asptttoulousenatation.core.document;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.asptttoulousenatation.core.server.dao.entity.document.LibelleGroupeEntity;

public class LibelleGroupeResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5252724899364170863L;
	private List<LibelleGroupeEntity> groupes;
	
	public LibelleGroupeResult() {
		groupes = Collections.emptyList();
	}

	public List<LibelleGroupeEntity> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<LibelleGroupeEntity> pGroupes) {
		groupes = pGroupes;
	}
}