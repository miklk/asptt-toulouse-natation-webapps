package com.asptttoulousenatation.core.server.dao.entity.club.group;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class GroupEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3111204833945561727L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
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
	
}