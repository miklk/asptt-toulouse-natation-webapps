package com.asptttoulousenatation.core.shared.user;

import java.util.Collection;
import java.util.HashSet;

public enum ProfileEnum {

	PUBLIC,
	ADMIN,
	NAGEUR,
	OFFICIEL;
	
	public static Collection<ProfileEnum> getProfiles(Collection<String> pProfileAsString) {
		final Collection<ProfileEnum> lResult = new HashSet<ProfileEnum>(pProfileAsString.size());
		for(String lProfile: pProfileAsString) {
			lResult.add(valueOf(lProfile));
		}
		return lResult;
	}
}