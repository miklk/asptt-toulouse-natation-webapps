package com.asptt.core.document;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentUploadAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4825870908337169228L;
	private String title;
	private String summary;
	private Set<String> libelles;
	
	public DocumentUploadAction() {
		
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