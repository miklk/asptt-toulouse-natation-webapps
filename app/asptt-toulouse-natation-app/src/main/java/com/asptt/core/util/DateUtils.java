package com.asptt.core.util;

import org.apache.commons.lang3.StringUtils;

public final class DateUtils {
	
	public static int[] getHour(int pMinutes) {
		final int lHour =  pMinutes / 60;
		final int lMinutes = pMinutes - (lHour * 60);
		return new int[] {lHour, lMinutes};
	}
	
	public static String formatMinutes(int pMinutes) {
		return StringUtils.leftPad(Integer.toString(pMinutes), 2, "0");
	}
}