package com.asptttoulousenatation.core.shared.swimmer;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface ISwimmerStatUi extends IsSerializable {

	public Long getUser();
	public void setUser(Long pUser);
	public String getSwimmer();
	public void setSwimmer(String pSwimmer);
	
}
