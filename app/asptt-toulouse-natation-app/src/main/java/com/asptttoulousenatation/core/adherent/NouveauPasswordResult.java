package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NouveauPasswordResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5487472818801154949L;
	private boolean exist;
	
	public NouveauPasswordResult() {
		exist = false;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean pExist) {
		exist = pExist;
	}
}
