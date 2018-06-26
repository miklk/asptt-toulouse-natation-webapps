package com.asptt.core.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptt.core.shared.user.UserUi;

@XmlRootElement
public class UserFindResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9090478363872615598L;
	private List<UserUi> users;
	
	public UserFindResult() {
		users = new ArrayList<>();
	}
	
	public void addUser(UserUi pUser) {
		users.add(pUser);
	}

	public List<UserUi> getUsers() {
		return users;
	}

	public void setUsers(List<UserUi> pUsers) {
		users = pUsers;
	}
	
}
