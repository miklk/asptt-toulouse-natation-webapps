package com.asptt.core.adherent;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UploadCertificatParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4825870908337169228L;
	private Long dossier;
	
	public Long getDossier() {
		return dossier;
	}

	public void setDossier(Long dossier) {
		this.dossier = dossier;
	}
}