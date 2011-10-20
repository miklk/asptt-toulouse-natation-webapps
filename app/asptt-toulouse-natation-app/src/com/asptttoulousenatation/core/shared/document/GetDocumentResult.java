package com.asptttoulousenatation.core.shared.document;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class GetDocumentResult implements Result {

	private List<DocumentUi> documents;
	
	public GetDocumentResult() {
	}

	public GetDocumentResult(List<DocumentUi> pDocuments) {
		super();
		documents = pDocuments;
	}

	public List<DocumentUi> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentUi> pDocuments) {
		documents = pDocuments;
	}
}