package com.asptt.core.inscription;

import java.io.Serializable;
import java.util.List;

import com.asptt.core.lang.CoupleValue;

public class DossierPaiementParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long dossierId;
	private String statutPaiement;
	private String commentaire;
	private int montantReel;
	private List<CoupleValue<String, String>> paiement;
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
	public List<CoupleValue<String, String>> getPaiement() {
		return paiement;
	}
	public void setPaiement(List<CoupleValue<String, String>> paiement) {
		this.paiement = paiement;
	}
}