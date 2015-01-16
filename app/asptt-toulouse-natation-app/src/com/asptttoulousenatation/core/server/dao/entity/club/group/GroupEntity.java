package com.asptttoulousenatation.core.server.dao.entity.club.group;

import javax.jdo.annotations.Persistent;
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
	
	@Persistent
	private String title;
	
	@Persistent
	private Boolean licenceFfn;
	
	@Persistent
	private Boolean inscription;
	@Persistent
	private Integer tarif;
	@Persistent
	private Integer tarif2;
	@Persistent
	private Integer tarif3;
	@Persistent
	private Integer tarif4;
	@Persistent
	private Integer tarifWeight;
	@Persistent
	private Boolean seanceunique;
	
	@Persistent
	private Boolean nouveau;
	
	@Persistent
	private String description;
	
	private Boolean enf;
	
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
}