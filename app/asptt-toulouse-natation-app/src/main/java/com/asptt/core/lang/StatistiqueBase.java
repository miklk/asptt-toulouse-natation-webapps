package com.asptt.core.lang;

import java.io.Serializable;

public class StatistiqueBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String statTitle;
	private Integer count;
	
	public StatistiqueBase() {
		
	}
	
	public StatistiqueBase(String statTitle, Integer count) {
		super();
		this.statTitle = statTitle;
		this.count = count;
	}
	public String getStatTitle() {
		return statTitle;
	}
	public void setStatTitle(String statTitle) {
		this.statTitle = statTitle;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}