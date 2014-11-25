package com.asptttoulousenatation.core.server.dao.entity.user;

import java.util.Date;

import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
public class UserDataEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Persistent
	private String firstName;
	
	@Persistent
	private String lastName;
	
	@Persistent
	private Date birthday;
	
	@Persistent
	private String birthdayPlace;
	
	@Persistent
	private String nationality;
	
	@Persistent
	private String phonenumber;
	
	@Persistent
	private String addressRoad;
	
	@Persistent
	private String addressAdditional;
	
	@Persistent
	private String addressCode;
	
	@Persistent
	private String addressCity;
	
	@Persistent
	private String gender;
	
	@Persistent
	private String measurementSwimsuit;
	
	@Persistent
	private String measurementTshirt;
	
	@Persistent
	private String measurementShort;

	@Persistent
	private String contactLastName;
	
	@Persistent
	private String contactFirstName;
	
	public UserDataEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
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