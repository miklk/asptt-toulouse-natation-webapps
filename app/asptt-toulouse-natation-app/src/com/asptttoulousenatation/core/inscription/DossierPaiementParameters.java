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
	private String modePaiement;
	private String numeroPaiement;
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
	public String getModePaiement() {
		return modePaiement;
	}
	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}
	public String getNumeroPaiement() {
		return numeroPaiement;
	}
	public void setNumeroPaiement(String numeroPaiement) {
		this.numeroPaiement = numeroPaiement;
	}

	
}