package com.asptttoulousenatation.core.server.dao.entity.club.group;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
public class GroupEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3111204833945561727L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private Boolean licenceFfn;
	
	
	private Boolean inscription;
	
	private Integer tarif;
	
	private Integer tarif2;
	
	private Integer tarif3;
	
	private Integer tarif4;
	
	private Integer tarifWeight;
	
	
	private Integer tarifUnique;
	
	private Integer tarifUnique2;
	
	private Integer tarifUnique3;
	
	private Integer tarifUnique4;
	
	
	private Boolean seanceunique;
	
	
	private Boolean nouveau;
	
	
	private String description;
	
	
	private Boolean enf;
	
	
	private Boolean competition;
	
	
	private Boolean secondes;
	
	
	private Integer capacite;
	
	
	private Integer occupe;
	
	public GroupEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public Boolean getLicenceFfn() {
		return licenceFfn;
	}

	public void setLicenceFfn(Boolean pLicenceFfn) {
		licenceFfn = pLicenceFfn;
	}

	public Boolean getInscription() {
		return inscription;
	}

	public void setInscription(Boolean pInscription) {
		inscription = pInscription;
	}

	public Integer getTarif() {
		return tarif;
	}

	public void setTarif(Integer pTarif) {
		tarif = pTarif;
	}

	public Integer getTarif2() {
		return tarif2;
	}

	public void setTarif2(Integer pTarif2) {
		tarif2 = pTarif2;
	}

	public Integer getTarif3() {
		return tarif3;
	}

	public void setTarif3(Integer pTarif3) {
		tarif3 = pTarif3;
	}

	public Integer getTarif4() {
		return tarif4;
	}

	public void setTarif4(Integer pTarif4) {
		tarif4 = pTarif4;
	}
	
	public static int getTarif(Integer pTarif) {
		return pTarif == null ? 0: pTarif;
	}

	public Boolean getSeanceunique() {
		return seanceunique;
	}

	public void setSeanceunique(Boolean pSeanceunique) {
		seanceunique = pSeanceunique;
	}

	public Integer getTarifWeight() {
		return tarifWeight;
	}

	public void setTarifWeight(Integer pTarifWeight) {
		tarifWeight = pTarifWeight;
	}

	public Boolean getNouveau() {
		return nouveau;
	}

	public void setNouveau(Boolean pNouveau) {
		nouveau = pNouveau;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String pDescription) {
		description = pDescription;
	}

	public Boolean getEnf() {
		return enf;
	}

	public void setEnf(Boolean pEnf) {
		enf = pEnf;
	}

	public Boolean getCompetition() {
		return competition;
	}

	public void setCompetition(Boolean competition) {
		this.competition = competition;
	}

	public Boolean getSecondes() {
		return secondes;
	}

	public void setSecondes(Boolean secondes) {
		this.secondes = secondes;
	}

	public Integer getTarifUnique() {
		return tarifUnique;
	}

	public void setTarifUnique(Integer tarifUnique) {
		this.tarifUnique = tarifUnique;
	}

	public Integer getTarifUnique2() {
		return tarifUnique2;
	}

	public void setTarifUnique2(Integer tarifUnique2) {
		this.tarifUnique2 = tarifUnique2;
	}

	public Integer getTarifUnique3() {
		return tarifUnique3;
	}

	public void setTarifUnique3(Integer tarifUnique3) {
		this.tarifUnique3 = tarifUnique3;
	}

	public Integer getTarifUnique4() {
		return tarifUnique4;
	}

	public void setTarifUnique4(Integer tarifUnique4) {
		this.tarifUnique4 = tarifUnique4;
	}

	public Integer getCapacite() {
		return capacite;
	}

	public void setCapacite(Integer capacite) {
		this.capacite = capacite;
	}

	public Integer getOccupe() {
		return occupe;
	}

	public void setOccupe(Integer occupe) {
		this.occupe = occupe;
	}
	
}