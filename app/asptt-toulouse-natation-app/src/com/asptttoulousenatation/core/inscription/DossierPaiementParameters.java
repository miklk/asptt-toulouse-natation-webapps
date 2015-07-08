package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;

public class DossierPaiementParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long dossierId;
	private String statutPaiement;
	private String commentaire;
	private int montantReel;
	public Long getDossierId() {
		return dossierId;
	}
	public void setDossierId(Long dossierId) {
		this.dossierId = dossierId;
	}
	public String getStatutPaiement() {
		return statutPaiement;
	}
	public void setStatutPaiement(String statutPaiement) {
		this.statutPaiement = statutPaiement;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public int getMontantReel() {
		return montantReel;
	}
	public void setMontantReel(int montantReel) {
		this.montantReel = montantReel;
	}
	
}