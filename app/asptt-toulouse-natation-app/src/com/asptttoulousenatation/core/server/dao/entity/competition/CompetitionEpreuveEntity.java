package com.asptttoulousenatation.core.server.dao.entity.competition;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
@XmlRootElement
public class CompetitionEpreuveEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String bassin;
	private String nage;
	private String distance;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBassin() {
		return bassin;
	}
	public void setBassin(String bassin) {
		this.bassin = bassin;
	}
	public String getNage() {
		return nage;
	}
	public void setNage(String nage) {
		this.nage = nage;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
}
