package com.asptttoulousenatation.core.security;

import org.springframework.security.core.GrantedAuthority;

public enum AppRole implements GrantedAuthority {
    ADMIN (0),
    PUBLIC(1),
	NAGEUR(2),
	OFFICIEL(3);

    private int bit;

    AppRole(int bit) {
        this.bit = bit;
    }

    public String getAuthority() {
        return toString();
    }

	public int getBit() {
		return bit;
	}

	public void setBit(int pBit) {
		bit = pBit;
	}
    
}