package com.asptttoulousenatation.core.authentication;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.joda.time.DateTime;

public class TokenManager {

	private static TokenManager INSTANCE;
	
	
	public Map<String, Long> tokens = Collections.synchronizedMap(new HashMap<String, Long>());
	private Map<String, Long> tokensTimestamp = Collections.synchronizedMap(new HashMap<String, Long>());
	
	public static TokenManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new TokenManager();
		}
		return INSTANCE;
	}
	
	public synchronized boolean contains(String token) {
		final boolean contains = tokens.containsKey(token);
		if(contains) {
			tokensTimestamp.put(token, DateTime.now().getMillis());
		}
		return contains;
	}
	
	public synchronized void put(String token, Long user) {
		tokens.put(token, user);
		tokensTimestamp.put(token, DateTime.now().getMillis());
	}
	
	public synchronized void remove(String token) {
		tokens.remove(token);
	}
	
	public synchronized Long getUser(String token) {
		if(tokensTimestamp.containsKey(token)) {
			tokensTimestamp.put(token, DateTime.now().getMillis());
		}
		return tokens.get(token);
	}
	
	public synchronized void clear() {
		tokens.clear();
		tokensTimestamp.clear();
	}
	
	public synchronized void expire(int minutes) {
		long threshold = DateTime.now().minusMinutes(minutes).getMillis();
		synchronized(tokensTimestamp) {
			Set<Entry<String, Long>> entries = tokensTimestamp.entrySet();
			for(Entry<String, Long> entry : entries) {
				if(entry.getValue() <= threshold) {
					tokensTimestamp.remove(entry.getKey());
				}
			}
		};
	}
}
