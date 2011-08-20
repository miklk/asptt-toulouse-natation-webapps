package com.asptttoulousenatation.client.util;


public final class Utils {

	public static int[] getTime(int pMinutes) {
		int lMinute = pMinutes % 60;
		int lHour = pMinutes / 60;
		return new int[] {lHour, lMinute};
	}
	
	public static String getTimeAsString(int pMinutes) {
		int[] lTime = getTime(pMinutes);
		final String lHour;
		if(lTime[0] < 10) {
			lHour = "0" + lTime[0];
		}
		else {
			lHour = Integer.toString(lTime[0]);
		}
		
		final String lMinute;
		if(lTime[1] < 10) {
			lMinute = "0" + lTime[1];
		}
		else {
			lMinute = Integer.toString(lTime[1]);
		}
		
		return lHour + ":" + lMinute;
	}
}
