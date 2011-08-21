package com.asptttoulousenatation.core.shared.user;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDataUi implements IsSerializable {

	private String firstName;
	private String lastName;
	private Date birthday;
	private String phonenumber;
	private String addressRoad;
	private String addressCode;
	private String addressCity;
	
	public UserDataUi() {
		
	}

	public UserDataUi(String pFirstName, String pLastName, Date pBirthday,
			String pPhonenumber, String pAddressRoad, String pAddressCode,
			String pAddressCity) {
		super();
		firstName = pFirstName;
		lastName = pLastName;
		birthday = pBirthday;
		phonenumber = pPhonenumber;
		addressRoad = pAddressRoad;
		addressCode = pAddressCode;
		addressCity = pAddressCity;
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
}