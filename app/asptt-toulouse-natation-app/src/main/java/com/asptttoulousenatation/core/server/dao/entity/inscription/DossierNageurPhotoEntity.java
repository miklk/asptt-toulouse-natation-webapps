package com.asptttoulousenatation.core.server.dao.entity.inscription;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;
import com.google.appengine.api.datastore.Blob;

@Entity
@XmlRootElement
public class DossierNageurPhotoEntity implements IEntity {

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
	
	private Blob photo;
	
	public DossierNageurPhotoEntity() {
		
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

	public Blob getPhoto() {
		return photo;
	}

	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
}