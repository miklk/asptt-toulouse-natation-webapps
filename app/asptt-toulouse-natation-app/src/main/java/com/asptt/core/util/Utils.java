package com.asptt.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.ws.rs.core.MediaType;

public class Utils {
	
	private static final Set<String> IMAGE_MEDIA_TYPE;
	
	static {
		IMAGE_MEDIA_TYPE = new HashSet<>();
		IMAGE_MEDIA_TYPE.add("image/jpeg");
		IMAGE_MEDIA_TYPE.add("image/png");
	}

	public static MessageDigest getMD5() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("MD5");
	}
	
	public static String format(Date pWhen, String pPattern) {
		SimpleDateFormat lFormatter = new SimpleDateFormat(pPattern, Locale.FRENCH);
		return lFormatter.format(pWhen);
	}
	
	public static boolean isImageMediaType(MediaType mediaType) {
		return IMAGE_MEDIA_TYPE.contains(mediaType.toString());
	}
}