package com.asptt.core.server.dao.entity.inscription;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.asptt.core.server.dao.entity.IEntity;

@Entity
@XmlRootElement
public class DossierStatEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207564448323062425L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String stat;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date computed;
	
	public DossierStatEntity() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}
	
	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public Date getComputed() {
		return computed;
	}

	public void setComputed(Date computed) {
		this.computed = computed;
	}

	@PrePersist
	@PreUpdate
	public void onPersist() {
		computed = new Date();
	}
}