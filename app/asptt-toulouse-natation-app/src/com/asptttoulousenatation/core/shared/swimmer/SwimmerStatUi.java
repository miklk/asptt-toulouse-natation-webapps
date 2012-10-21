package com.asptttoulousenatation.core.shared.swimmer;

import java.util.List;


public class SwimmerStatUi implements ISwimmerStatUi {

	private Long id;
	private Long user;
	private String swimmer;
	private List<SwimmerStatDataUi> data;
	
	public SwimmerStatUi() {
		
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long pId) {
		id = pId;
	}


	public void setData(List<SwimmerStatDataUi> pData) {
		data = pData;
	}


	public String getSwimmer() {
		return swimmer;
	}

	public void setSwimmer(String pSwimmer) {
		swimmer = pSwimmer;
	}

	public List<SwimmerStatDataUi> getData() {
		return data;
	}

	public void setDistances(List<SwimmerStatDataUi> pData) {
		data = pData;
	}


	public Long getUser() {
		return user;
	}


	public void setUser(Long pUser) {
		user = pUser;
	}
	
}