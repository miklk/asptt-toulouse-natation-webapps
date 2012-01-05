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
	private String addressRoad;
	private String addressAdditional;
	private String addressCode;
	private String addressCity;
	private String gender;
	private String measurementSwimsuit;
	private String measurementTshirt;
	private String measurementShort;
	
	public UpdateUserAction() {
		
	}

	public UpdateUserAction(Long pId, String pEmailAddress, boolean pValidated,
			Set<String> pProfiles, Set<Long> pSlots, String pLastName,
			String pFirstName, Date pBirthday, String pPhonenumber,
			String pAddressRoad, String pAddressAdditional,
			String pAddressCode, String pAddressCity, String pGender,
			String pMeasurementSwimsuit, String pMeasurementTshirt,
			String pMeasurementShort) {
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
		addressRoad = pAddressRoad;
		addressAdditional = pAddressAdditional;
		addressCode = pAddressCode;
		addressCity = pAddressCity;
		gender = pGender;
		measurementSwimsuit = pMeasurementSwimsuit;
		measurementTshirt = pMeasurementTshirt;
		measurementShort = pMeasurementShort;
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

	public String getAddressAdditional() {
		return addressAdditional;
	}

	public void setAddressAdditional(String pAddressAdditional) {
		addressAdditional = pAddressAdditional;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String pGender) {
		gender = pGender;
	}

	public String getMeasurementSwimsuit() {
		return measurementSwimsuit;
	}

	public void setMeasurementSwimsuit(String pMeasurementSwimsuit) {
		measurementSwimsuit = pMeasurementSwimsuit;
	}

	public String getMeasurementTshirt() {
		return measurementTshirt;
	}

	public void setMeasurementTshirt(String pMeasurementTshirt) {
		measurementTshirt = pMeasurementTshirt;
	}

	public String getMeasurementShort() {
		return measurementShort;
	}

	public void setMeasurementShort(String pMeasurementShort) {
		measurementShort = pMeasurementShort;
	}
}