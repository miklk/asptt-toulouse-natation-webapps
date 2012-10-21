package com.asptttoulousenatation.core.shared.swimmer;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SwimmerUi implements IsSerializable {

	private Long id;
	private String name;
	private boolean stat;
	
	public SwimmerUi() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String pName) {
		name = pName;
	}

	public boolean isStat() {
		return stat;
	}

	public void setStat(boolean pStat) {
		stat = pStat;
	}
	
}