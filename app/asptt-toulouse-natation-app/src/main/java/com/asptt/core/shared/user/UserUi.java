package com.asptt.core.shared.user;

import java.io.Serializable;
import java.util.Set;

public class UserUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String emailAddress;
	private boolean validated;
	private Set<String> profiles;
	private UserDataUi userData;
	private Set<Long> slots;
	
	public UserUi() {
	}

	public UserUi(Long pId, String pEmailAddress, boolean pValidated,
			Set<String> pProfiles, UserDataUi pUserData, Set<Long> pSlots) {
		super();
		id = pId;
		emailAddress = pEmailAddress;
		validated = pValidated;
		profiles = pProfiles;
		userData = pUserData;
		slots = pSlots;
	}
	
	public void addAccess(String access) {
		profiles.add(access);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String pEmailAddress) {
		emailAddress = pEmailAddress;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean pValidated) {
		validated = pValidated;
	}

	public Set<String> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<String> pProfiles) {
		profiles = pProfiles;
	}

	public UserDataUi getUserData() {
		return userData;
	}

	public void setUserData(UserDataUi pUserData) {
		userData = pUserData;
	}

	public Set<Long> getSlots() {
		return slots;
	}

	public void setSlots(Set<Long> pSlots) {
		slots = pSlots;
	}
}