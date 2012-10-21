package com.asptttoulousenatation.core.shared.user;

import java.util.Set;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerUi;
import com.google.gwt.user.client.rpc.IsSerializable;

public class UserUi implements IsSerializable {

	private Long id;
	private String emailAddress;
	private boolean validated;
	private Set<String> profiles;
	private UserDataUi userData;
	private Set<Long> slots;
	
	private SwimmerUi swimmer;
	
	public UserUi() {
	}

	public UserUi(Long pId, String pEmailAddress, boolean pValidated,
			Set<String> pProfiles, UserDataUi pUserData, Set<Long> pSlots, SwimmerUi pSwimmer) {
		super();
		id = pId;
		emailAddress = pEmailAddress;
		validated = pValidated;
		profiles = pProfiles;
		userData = pUserData;
		slots = pSlots;
		swimmer = pSwimmer;
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

	public SwimmerUi getSwimmer() {
		return swimmer;
	}

	public void setSwimmer(SwimmerUi pSwimmer) {
		swimmer = pSwimmer;
	}
}