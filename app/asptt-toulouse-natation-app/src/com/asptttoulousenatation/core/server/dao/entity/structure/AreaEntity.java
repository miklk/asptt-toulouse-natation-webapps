package com.asptttoulousenatation.core.server.dao.entity.structure;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.apache.commons.lang.StringUtils;

import com.asptttoulousenatation.core.server.dao.entity.Entity;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class AreaEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 819058375507323726L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
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