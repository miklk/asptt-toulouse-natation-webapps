package com.asptt.core.server.dao.entity.inscription;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.asptt.core.server.dao.entity.IEntity;
import com.google.appengine.api.datastore.Blob;

@Entity
@XmlRootElement
public class DossierCertificatEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207564448323062425L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Identifier of DossierNageurEntity
	 */
	private Long dossier;
	
	private Blob certificatmedical;
	
	private String fileName;
	
	public DossierCertificatEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDossier() {
		return dossier;
	}

	public void setDossier(Long dossier) {
		this.dossier = dossier;
	}

	public Blob getCertificatmedical() {
		return certificatmedical;
	}

	public void setCertificatmedical(Blob certificatmedical) {
		this.certificatmedical = certificatmedical;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}