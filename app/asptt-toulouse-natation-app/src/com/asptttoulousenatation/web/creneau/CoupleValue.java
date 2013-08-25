package com.asptttoulousenatation.web.creneau;

public class CoupleValue<F extends Object, S extends Object> {

	private String first;
	private S second;
	
	public CoupleValue() {
		
	}

	public CoupleValue(String pFirst, S pSecond) {
		super();
		first = pFirst;
		second = pSecond;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String pFirst) {
		first = pFirst;
	}

	public S getSecond() {
		return second;
	}

	public void setSecond(S pSecond) {
		second = pSecond;
	}
}