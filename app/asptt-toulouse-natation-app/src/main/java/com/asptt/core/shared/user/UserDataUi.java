package com.asptt.core.shared.user;

import java.io.Serializable;
import java.util.Date;

public class UserDataUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private Date birthday;
	private String birthdayPlace;
	private String nationality;
	private String phonenumber;
	private String addressRoad;
	private String addressAdditional;
	private String addressCode;
	private String addressCity;
	private String gender;
	private String measurementSwimsuit;
	private String measurementTshirt;
	private String measurementShort;
	private String contactLastName;
	private String contactFirstName;

	public UserDataUi() {

	}

	public UserDataUi(String pFirstName, String pLastName, Date pBirthday,
			String pBirthdayPlace, String pNationality, String pPhonenumber,
			String pAddressRoad, String pAddressAdditional,
			String pAddressCode, String pAddressCity, String pGender,
			String pMeasurementSwimsuit, String pMeasurementTshirt,
			String pMeasurementShort, String pContactLastName,
			String pContactFirstName) {
		super();
		firstName = pFirstName;
		lastName = pLastName;
		birthday = pBirthday;
		birthdayPlace = pBirthdayPlace;
		nationality = pNationality;
		phonenumber = pPhonenumber;
		addressRoad = pAddressRoad;
		addressAdditional = pAddressAdditional;
		addressCode = pAddressCode;
		addressCity = pAddressCity;
		gender = pGender;
		measurementSwimsuit = pMeasurementSwimsuit;
		measurementTshirt = pMeasurementTshirt;
		measurementShort = pMeasurementShort;
		contactLastName = pContactLastName;
		contactFirstName = pContactFirstName;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String pFirstName) {
		firstName = pFirstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String pLastName) {
		lastName = pLastName;
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

	public String getBirthdayPlace() {
		return birthdayPlace;
	}

	public void setBirthdayPlace(String pBirthdayPlace) {
		birthdayPlace = pBirthdayPlace;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String pNationality) {
		nationality = pNationality;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String pContactLastName) {
		contactLastName = pContactLastName;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String pContactFirstName) {
		contactFirstName = pContactFirstName;
	}
}