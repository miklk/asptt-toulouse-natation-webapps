package com.asptttoulousenatation.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

	public static MessageDigest getMD5() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("MD5");
	}
	
	public static String format(Date pWhen, String pPattern) {
		SimpleDateFormat lFormatter = new SimpleDateFormat(pPattern, Locale.FRENCH);
		return lFormatter.format(pWhen);
	}
}