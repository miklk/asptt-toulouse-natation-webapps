package com.asptttoulousenatation.core.swimmer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatYearUi;

@XmlRootElement
public class SwimmerStatYearListResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7062740519408349271L;
	private List<SwimmerStatYearUi> nageurs;
	private Long begin;
	private Long end;
	
	public SwimmerStatYearListResult() {
		nageurs = new ArrayList<>();
	}
	
	public void addNageur(SwimmerStatYearUi pNageur) {
		nageurs.add(pNageur);
	}

	public List<SwimmerStatYearUi> getNageurs() {
		return nageurs;
	}

	public void setNageurs(List<SwimmerStatYearUi> pNageurs) {
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
}