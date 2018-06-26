package com.asptt.core.document;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptt.core.shared.document.DocumentUi;

@XmlRootElement
public class DocumentListResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5282976561465622207L;
	private List<DocumentUi> documents;
	
	public DocumentListResult() {
		
	}

	public List<DocumentUi> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentUi> pDocuments) {
		documents = pDocuments;
	}
}