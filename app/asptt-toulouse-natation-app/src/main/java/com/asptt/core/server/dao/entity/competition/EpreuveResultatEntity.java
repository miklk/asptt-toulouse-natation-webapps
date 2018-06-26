package com.asptt.core.server.dao.entity.competition;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.asptt.core.server.dao.entity.IEntity;

@Entity
@XmlRootElement
public class EpreuveResultatEntity implements IEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long time;
	private Long nageur;
	private Long epreuve;
	private Long competition;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Long getNageur() {
		return nageur;
	}
	public void setNageur(Long nageur) {
		this.nageur = nageur;
	}
	public Long getEpreuve() {
		return epreuve;
	}
	public void setEpreuve(Long epreuve) {
		this.epreuve = epreuve;
	}
	public Long getCompetition() {
		return competition;
	}
	public void setCompetition(Long competition) {
		this.competition = competition;
	}
	
}