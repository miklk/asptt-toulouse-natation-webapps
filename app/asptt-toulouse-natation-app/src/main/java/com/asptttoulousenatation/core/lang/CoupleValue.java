package com.asptttoulousenatation.core.lang;

public class CoupleValue<F extends Object, S extends Object> {

	private F first;
	private S second;
	
	public CoupleValue() {
		
	}

	public CoupleValue(F pFirst, S pSecond) {
		super();
		first = pFirst;
		second = pSecond;
	}

	public F getFirst() {
		return first;
	}

	public void setFirst(F pFirst) {
		first = pFirst;
	}

	public S getSecond() {
		return second;
	}

	public void setSecond(S pSecond) {
		second = pSecond;
	}
}