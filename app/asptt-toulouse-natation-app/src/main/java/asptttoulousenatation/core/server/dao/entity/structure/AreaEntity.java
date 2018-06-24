package com.asptttoulousenatation.core.server.dao.entity.structure;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;

@Entity
@XmlRootElement
public class AreaEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 819058375507323726L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String title;
	
	
	private String profile;
	
	
	private boolean shortcut;
	
	
	private short order;
	
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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String pProfile) {
		profile = pProfile;
	}
	
	public void setProfile(ProfileEnum pProfile) {
		profile = pProfile.toString();
	}

	public boolean isShortcut() {
		return shortcut;
	}

	public void setShortcut(boolean pShortcut) {
		shortcut = pShortcut;
	}

	public short getOrder() {
		return order;
	}

	public void setOrder(short pOrder) {
		order = pOrder;
	}
}