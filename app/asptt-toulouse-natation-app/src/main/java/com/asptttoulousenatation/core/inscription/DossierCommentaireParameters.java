package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;

public class DossierCommentaireParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long dossier;
	private String commentaire;
	
	public Long getDossier() {
		return dossier;
	}
	public void setDossier(Long dossier) {
		this.dossier = dossier;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
}
