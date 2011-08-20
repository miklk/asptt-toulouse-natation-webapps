package com.asptttoulousenatation.shared.init;

import java.util.Collection;

import net.customware.gwt.dispatch.shared.Action;

import com.asptttoulousenatation.core.shared.user.ProfileEnum;

public class InitUserSpaceAction implements Action<InitUserSpaceResult> {

	private Collection<ProfileEnum> profiles;
	
	public InitUserSpaceAction() {
		
	}

	public InitUserSpaceAction(Collection<ProfileEnum> pProfiles) {
		super();
		profiles = pProfiles;
	}

	public Collection<ProfileEnum> getProfiles() {
		return profiles;
	}

	public void setProfiles(Collection<ProfileEnum> pProfiles) {
		profiles = pProfiles;
	}
}