package com.asptttoulousenatation.client.util;

import java.util.Collection;

public final class CollectionUtils {

	public static boolean isEmpty(Collection<? extends Object> pCollection) {
		final boolean lResult;
		if(pCollection == null) {
			lResult = true;
		} else {
			lResult = pCollection.isEmpty();
		}
		return lResult;
	}
	
	public static boolean isNotEmpty(Collection<? extends Object> pCollection) {
		return !isEmpty(pCollection);
	}
}
