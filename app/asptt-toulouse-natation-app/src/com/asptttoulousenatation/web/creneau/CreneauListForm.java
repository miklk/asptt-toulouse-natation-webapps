package com.asptttoulousenatation.web.creneau;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreneauListForm implements Serializable {

	private static final long serialVersionUID = 993403115694674650L;
	private String action;
	private String piscine;
	
	private List<CreneauListResultBean> results;
	
	public CreneauListForm() {
		action = "load";
		piscine = "Léo Lagrange";
		results = new ArrayList<CreneauListResultBean>(0);
	}

	public String getPiscine() {
		return piscine;
	}

	public void setPiscine(String pPiscine) {
		piscine = pPiscine;
	}

	public List<CreneauListResultBean> getResults() {
		return results;
	}

	public void setResults(List<CreneauListResultBean> pResults) {
		results = pResults;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String pAction) {
		action = pAction;
	}
}