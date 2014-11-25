package com.asptttoulousenatation.core.server.dao.entity.structure;

import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;

@Entity
public class AreaEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 819058375507323726L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Persistent
	private String title;
	
	@Persistent
	private String profile;
	
	@Persistent
	private boolean shortcut;
	
	@Persistent
	private short order;
	
	public AreaEntity() {
		id = 0l;
		title = StringUtils.EMPTY;
		profile = ProfileEnum.ADMIN.toString();
		shortcut = false;
		order = 0;
	}

	public AreaEntity(Long pId, String pTitle, ProfileEnum pProfile, boolean pShortcut, short pOrder) {
		this();
		id = pId;
		title = pTitle;
		profile = pProfile.toString();
		shortcut = pShortcut;
		order = pOrder;
	}
	public AreaEntity(Long pId, String pTitle, ProfileEnum pProfile, short pOrder) {
		this(pId, pTitle, pProfile, false, pOrder);
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