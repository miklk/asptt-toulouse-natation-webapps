package com.asptttoulousenatation.shared.userspace.admin.user;

import java.util.Date;
import java.util.Set;

import net.customware.gwt.dispatch.shared.Action;

public class CreateUserAction implements Action<CreateUserResult> {

	private String emailAddress;
	private boolean validated;
	private Set<String> profiles;
	private Set<Long> slots;
	private String lastName;
	private String firstName;
	private Date birthday;
	private String phonenumber;
	private String addressRoad;
	private String addressCode;
	private String addressCity;
	
	public CreateUserAction() {
		
	}

	public CreateUserAction(String pEmailAddress, boolean pValidated,
			Set<String> pProfiles, Set<Long> pSlots, String pLastName,
			String pFirstName, Date pBirthday, String pPhonenumber,
			String pAddressRoad, String pAddressCode, String pAddressCity) {
		super();
		emailAddress = pEmailAddress;
		validated = pValidated;
		profiles = pProfiles;
		slots = pSlots;
		lastName = pLastName;
		firstName = pFirstName;
		birthday = pBirthday;
		phonenumber = pPhonenumber;
		addressRoad = pAddressRoad;
		addressCode = pAddressCode;
		addressCity = pAddressCity;
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

	public Set<Long> getSlots() {
		return slots;
	}

	public void setSlots(Set<Long> pSlots) {
		slots = pSlots;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String pLastName) {
		lastName = pLastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String pFirstName) {
		firstName = pFirstName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date pBirthday) {
		birthday = pBirthday;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String pPhonenumber) {
		phonenumber = pPhonenumber;
	}

	public String getAddressRoad() {
		return addressRoad;
	}

	public void setAddressRoad(String pAddressRoad) {
		addressRoad = pAddressRoad;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String pAddressCode) {
		addressCode = pAddressCode;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String pAddressCity) {
		addressCity = pAddressCity;
	}
}