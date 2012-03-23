package com.asptttoulousenatation.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

	public static MessageDigest getMD5() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("MD5");
	}
}
