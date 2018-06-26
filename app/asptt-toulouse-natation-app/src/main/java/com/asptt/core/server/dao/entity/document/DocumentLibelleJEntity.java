package com.asptt.core.server.dao.entity.document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptt.core.server.dao.entity.IEntity;

@Entity
public class DocumentLibelleJEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6284908408392347409L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long document;
	private Long libelle;
	
	public DocumentLibelleJEntity() {
		
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long pId) {
		id = pId;
	}


	public Long getDocument() {
		return document;
	}

	public void setDocument(Long pDocument) {
		document = pDocument;
	}

	public Long getLibelle() {
		return libelle;
	}

	public void setLibelle(Long pLibelle) {
		libelle = pLibelle;
	}
}