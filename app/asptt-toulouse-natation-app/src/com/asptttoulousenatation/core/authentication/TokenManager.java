package com.asptttoulousenatation.core.authentication;

import java.util.HashMap;
import java.util.Map;

public class TokenManager {

	private static TokenManager INSTANCE;
	
	
	public Map<String, Long> tokens = new HashMap<String, Long>();
	
	public static TokenManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new TokenManager();
		}
		return INSTANCE;
	}
	
	public synchronized boolean contains(String token) {
		return tokens.containsKey(token);
	}
	
	public synchronized void put(String token, Long user) {
		tokens.put(token, user);
	}
	
	public synchronized void remove(String token) {
		tokens.remove(token);
	}
	
	public synchronized Long getUser(String token) {
		return tokens.get(token);
	}
}
