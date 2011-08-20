package com.asptttoulousenatation.shared.userspace.admin.user;

import java.util.Date;
import java.util.Set;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateUserAction implements Action<UpdateUserResult> {

	private Long id;
	private String emailAddress;
	private boolean validated;
	private Set<String> profiles;
	private Set<Long> slots;
	private String lastName;
	private String firstName;
	private Date birthday;
	private String phonenumber;
	
	public UpdateUserAction() {
		
	}

	public UpdateUserAction(Long pId, String pEmailAddress, boolean pValidated,
			Set<String> pProfiles, Set<Long> pSlots, String pLastName,
			String pFirstName, Date pBirthday, String pPhonenumber) {
		super();
		id = pId;
		emailAddress = pEmailAddress;
		validated = pValidated;
		profiles = pProfiles;
		slots = pSlots;
		lastName = pLastName;
		firstName = pFirstName;
		birthday = pBirthday;
		phonenumber = pPhonenumber;
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
}