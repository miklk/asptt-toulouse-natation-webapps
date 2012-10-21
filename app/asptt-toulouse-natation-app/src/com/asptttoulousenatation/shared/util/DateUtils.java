package com.asptttoulousenatation.shared.util;

import java.util.Date;

public final class DateUtils {

	public boolean isSameDate(Date pDate1, Date pDate2) {
		return pDate1.getDate() == pDate2.getDate()
				&& pDate1.getMonth() == pDate2.getMonth()
				&& pDate1.getYear() == pDate2.getYear();
	}
	
	public static int[] getHour(int pMinutes) {
		final int lHour =  pMinutes / 60;
		final int lMinutes = pMinutes - (lHour * 60);
		return new int[] {lHour, lMinutes};
	}
}