package com.asptttoulousenatation.core.server.dao.entity;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ActuEntity implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6950881769888984570L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String title;
	
	@Persistent
	private String summary;
	
	@Persistent
	private Text content;
	
	@Persistent
	private Date creationDate;
	
	@Persistent
	private String imageUrl;
	
	@Persistent
	private Boolean competition;
	
	public ActuEntity() {
		
	}

	public ActuEntity(Long pId, String pTitle, String pSummary,
			Text pContent, Date pCreationDate, String pImageUrl, Boolean pCompetition) {
		id = pId;
		title = pTitle;
		summary = pSummary;
		content = pContent;
		creationDate = pCreationDate;
		imageUrl = pImageUrl;
		competition = pCompetition;
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
	
}