package com.asptttoulousenatation.core.document;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentUpdateResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 800865565165358469L;
	private boolean success;
	
	public DocumentUpdateResult() {
		success = false;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean pSuccess) {
		success = pSuccess;
	}
}