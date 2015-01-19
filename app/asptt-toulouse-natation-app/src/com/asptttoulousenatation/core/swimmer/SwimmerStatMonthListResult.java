package com.asptttoulousenatation.core.swimmer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatMonthUi;
import com.asptttoulousenatation.web.creneau.CoupleValue;

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
		for(int i = 0; i < 5; i++) {
			weeks.add(new CoupleValue<>(0l, 0l));
		}
	}
	
	public void addWeek(int week, CoupleValue<Long, Long> pWeek) {
		weeks.set(week, pWeek);
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