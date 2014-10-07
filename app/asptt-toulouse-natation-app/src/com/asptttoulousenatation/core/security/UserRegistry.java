package com.asptttoulousenatation.core.security;

public interface UserRegistry {
	  GaeUser findUser(String userId);
	  void registerUser(GaeUser newUser);
	  void removeUser(String userId);
	}
