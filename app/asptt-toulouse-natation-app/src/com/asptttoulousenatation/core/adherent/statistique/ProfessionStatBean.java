package com.asptttoulousenatation.core.adherent.statistique;

public class ProfessionStatBean {

	private String profession;
	private int count;
	
	public void add() {
		count++;
	}
	
	public String getProfession() {
		return profession;
	}
	public void setProfession(String pProfession) {
		profession = pProfession;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int pCount) {
		count = pCount;
	}
	
	
}
