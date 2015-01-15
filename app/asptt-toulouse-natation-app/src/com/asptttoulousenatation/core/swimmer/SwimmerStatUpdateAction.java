package com.asptttoulousenatation.core.swimmer;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;

@XmlRootElement
public class SwimmerStatUpdateAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dayTime;
	
	private Long day;
	
	private SwimmerStatUi stat;
	
	public SwimmerStatUpdateAction() {
		
	}

	public String getDayTime() {
		return dayTime;
	}

	public void setDayTime(String pDayTime) {
		dayTime = pDayTime;
	}

	public SwimmerStatUi getStat() {
		return stat;
	}

	public void setStat(SwimmerStatUi pStat) {
		stat = pStat;
	}

	public Long getDay() {
		return day;
	}

	public void setDay(Long pDay) {
		day = pDay;
	}
	
}