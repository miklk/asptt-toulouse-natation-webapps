package com.asptttoulousenatation.core.adherent.statistique;

public class LocalisationStatBean {

	private String codepostal;
	private int count;
	
	public void add() {
		count++;
	}
	
	public String getCodepostal() {
		return codepostal;
	}
	public void setCodepostal(String pCodepostal) {
		codepostal = pCodepostal;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int pCount) {
		count = pCount;
	}
	
}
