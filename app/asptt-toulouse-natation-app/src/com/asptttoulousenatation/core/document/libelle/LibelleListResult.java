package com.asptttoulousenatation.core.document.libelle;

import java.io.Serializable;
import java.util.Collection;

import com.asptttoulousenatation.core.shared.document.libelle.LibelleUi;

public class LibelleListResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 893031418494042573L;
	private Collection<LibelleUi> libelles;
	private Collection<String> wholeLibelles;
	
	public LibelleListResult() {
		
	}

	public Collection<LibelleUi> getLibelles() {
		return libelles;
	}

	public void setLibelles(Collection<LibelleUi> pLibelles) {
		libelles = pLibelles;
	}

	public Collection<String> getWholeLibelles() {
		return wholeLibelles;
	}

	public void setWholeLibelles(Collection<String> pWholeLibelles) {
		wholeLibelles = pWholeLibelles;
	}

}