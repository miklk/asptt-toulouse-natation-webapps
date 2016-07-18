package com.asptttoulousenatation.core.swimmer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.core.lang.CoupleValue;

public class SwimmerStatMonthListResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SwimmerStatMonthUi> nageurs;
	private List<CoupleValue<Long, Long>> weeks;
	private Long begin;
	private Long end;
	
	public SwimmerStatMonthListResult() {
		nageurs = new ArrayList<>();
		weeks = new ArrayList<CoupleValue<Long,Long>>();
	}
	
	public void addWeek(CoupleValue<Long, Long> pWeek) {
		weeks.add(pWeek);
	}
	
	public void addNageur(SwimmerStatMonthUi pNageur) {
		nageurs.add(pNageur);
	}

	public List<SwimmerStatMonthUi> getNageurs() {
		return nageurs;
	}

	public void setNageurs(List<SwimmerStatMonthUi> pNageurs) {
		nageurs = pNageurs;
	}

	public Long getBegin() {
		return begin;
	}

	public void setBegin(Long pBegin) {
		begin = pBegin;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long pEnd) {
		end = pEnd;
	}

	public List<CoupleValue<Long, Long>> getWeeks() {
		return weeks;
	}

	public void setWeeks(List<CoupleValue<Long, Long>> pWeeks) {
		weeks = pWeeks;
	}
}