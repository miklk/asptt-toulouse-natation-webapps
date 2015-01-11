package com.asptttoulousenatation.core.document;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentUpdateAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -385454121619543520L;
	
	private String title;
	private String summary;
	private Set<String> libelles;
	
	public DocumentUpdateAction() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String pSummary) {
		summary = pSummary;
	}

	public Set<String> getLibelles() {
		return libelles;
	}

	public void setLibelles(Set<String> pLibelles) {
		libelles = pLibelles;
	}
}
