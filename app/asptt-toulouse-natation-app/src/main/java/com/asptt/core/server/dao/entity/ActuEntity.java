
package com.asptt.core.server.dao.entity;

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

import com.google.appengine.api.datastore.Text;


@Entity
@XmlRootElement
public class ActuEntity implements IEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private Text content;
	
	private Date creationDate;
	
	private String imageUrl;
	
	private Boolean competition;
	
	private Date expiration;
	
	private String statut;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	private String createdBy;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
	private String updatedBy;
	
	@PrePersist
	public void onPersist() {
		created = new Date();
		updated = new Date();
	}
	
	@PreUpdate
	public void onUpdate() {
		updated = new Date();
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

	public Text getContent() {
		return content;
	}

	public void setContent(Text pContent) {
		content = pContent;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date pCreationDate) {
		creationDate = pCreationDate;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String pImageUrl) {
		imageUrl = pImageUrl;
	}

	public Boolean getCompetition() {
		return competition;
	}

	public void setCompetition(Boolean pCompetition) {
		competition = pCompetition;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date pExpiration) {
		expiration = pExpiration;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}