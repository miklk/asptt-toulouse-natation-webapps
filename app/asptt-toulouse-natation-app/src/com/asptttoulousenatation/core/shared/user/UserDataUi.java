package com.asptttoulousenatation.core.shared.user;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDataUi implements IsSerializable {

	private String firstName;
	private String lastName;
	private Date birthday;
	private String phonenumber;
	
	public UserDataUi() {
		
	}

	public UserDataUi(String pFirstName, String pLastName, Date pBirthday,
			String pPhonenumber) {
		super();
		firstName = pFirstName;
		lastName = pLastName;
		birthday = pBirthday;
		phonenumber = pPhonenumber;
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
}