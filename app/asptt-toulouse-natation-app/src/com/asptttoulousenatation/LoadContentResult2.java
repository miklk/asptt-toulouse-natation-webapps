package com.asptttoulousenatation;

import java.util.List;

import com.asptttoulousenatation.core.shared.document.DocumentUi;

import net.customware.gwt.dispatch.shared.Result;

public class LoadContentResult2 implements Result {

	private String contenu;
	private List<DocumentUi> documents;
	
	public LoadContentResult2() {
		
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String pContenu) {
		contenu = pContenu;
	}

	public List<DocumentUi> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentUi> pDocuments) {
		documents = pDocuments;
	}
	
}