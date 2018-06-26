package com.asptt.core.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asptt.core.shared.document.DocumentUi;

public class DocumentByLibelleResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7377968408120705793L;
	private Map<String, List<DocumentUi>> documents;
	
	public DocumentByLibelleResult() {
		documents = new HashMap<>();
	}
	
	public void add(String pLibelle, DocumentUi pDocument) {
		final List<DocumentUi> docs;
		if(documents.containsKey(pLibelle)) {
			docs = documents.get(pLibelle);
		} else {
			docs = new ArrayList<>();
			documents.put(pLibelle, docs);
		}
		docs.add(pDocument);
	}

	public Map<String, List<DocumentUi>> getDocuments() {
		return documents;
	}

	public void setDocuments(Map<String, List<DocumentUi>> pDocuments) {
		documents = pDocuments;
	}

}