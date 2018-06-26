
package com.asptt.core.server.dao.entity.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptt.core.server.dao.entity.IEntity;

@Entity
public class UserEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String emailaddress;
	
	private boolean validated;
	
	private String password;
	
	private Set<String> profiles;

	private Set<Long> slots;
	
	private Long userData;
	
	private UserAuthenticationTypeEnum authentication;
	
	public UserEntity() {
		
	}

	public UserEntity(Long pId, String pEmailaddress, boolean pValidated,
			String pPassword, Set<String> pProfiles, Set<Long> pSlots,
			Long pUserData) {
		super();
		id = pId;
		emailaddress = pEmailaddress;
		validated = pValidated;
		password = pPassword;
		profiles = pProfiles;
		slots = pSlots;
		userData = pUserData;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String pEmailaddress) {
		emailaddress = pEmailaddress;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean pValidated) {
		validated = pValidated;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pPassword) {
		password = pPassword;
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

	public Long getUserData() {
		return userData;
	}

	public void setUserData(Long pUserData) {
		userData = pUserData;
	}

	public void addSlot(Long pSlot) {
		if(slots == null) {
			slots = new HashSet<Long>();
		}
		slots.add(pSlot);
	}

	public UserAuthenticationTypeEnum getAuthentication() {
		return authentication;
	}

	public void setAuthentication(UserAuthenticationTypeEnum pAuthentication) {
		authentication = pAuthentication;
	}
	
}