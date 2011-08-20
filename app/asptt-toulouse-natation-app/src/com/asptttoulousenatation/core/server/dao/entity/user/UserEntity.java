package com.asptttoulousenatation.core.server.dao.entity.user;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class UserEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String emailaddress;
	
	@Persistent
	private boolean validated;
	
	@Persistent
	private String password;
	
	@Persistent
	private Set<String> profiles;

	@Persistent
	private Set<Long> slots;
	
	@Persistent
	private Long userData;
	
	
	public UserEntity() {
		
	}

	public UserEntity(Long pId, String pEmailaddress, boolean pValidated,
			String pPassword, Set<String> pProfiles, Set<Long> pSlots,
			Long pUserData) {
		super();
		id = pId;
		emailaddress = pEmailaddress;
		validated = pValidated;
		password = pPassword;
		profiles = pProfiles;
		slots = pSlots;
		userData = pUserData;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String pEmailaddress) {
		emailaddress = pEmailaddress;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean pValidated) {
		validated = pValidated;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pPassword) {
		password = pPassword;
	}

	public Set<String> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<String> pProfiles) {
		profiles = pProfiles;
	}

	public Set<Long> getSlots() {
		return slots;
	}

	public void setSlots(Set<Long> pSlots) {
		slots = pSlots;
	}

	public Long getUserData() {
		return userData;
	}

	public void setUserData(Long pUserData) {
		userData = pUserData;
	}

	public void addSlot(Long pSlot) {
		if(slots == null) {
			slots = new HashSet<Long>();
		}
		slots.add(pSlot);
	}
	
}