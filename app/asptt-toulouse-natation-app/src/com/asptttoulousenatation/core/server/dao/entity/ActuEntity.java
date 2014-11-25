package com.asptttoulousenatation.core.server.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Text;


@Entity
public class ActuEntity implements IEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String summary;
	
	private Text content;
	
	private Date creationDate;
	
	private String imageUrl;
	
	private Boolean competition;
	
	private Date expiration;
	
	public ActuEntity() {
		
	}

	public ActuEntity(Long pId, String pTitle, String pSummary,
			Text pContent, Date pCreationDate, String pImageUrl, Boolean pCompetition, Date pExpiration) {
		id = pId;
		title = pTitle;
		summary = pSummary;
		content = pContent;
		creationDate = pCreationDate;
		imageUrl = pImageUrl;
		competition = pCompetition;
		expiration = pExpiration;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String pSummary) {
		summary = pSummary;
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
	
}