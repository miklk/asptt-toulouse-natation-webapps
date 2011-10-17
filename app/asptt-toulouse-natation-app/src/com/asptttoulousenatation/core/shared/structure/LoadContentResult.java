package com.asptttoulousenatation.core.shared.structure;

import java.util.List;

import com.asptttoulousenatation.core.shared.document.DocumentUi;

import net.customware.gwt.dispatch.shared.Result;

public class LoadContentResult implements Result {

	private byte[] data;
	private List<DocumentUi> documents;
	
	public LoadContentResult() {
		
	}

	public LoadContentResult(byte[] pData, List<DocumentUi> pDocuments) {
		super();
		data = pData;
		documents = pDocuments;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] pData) {
		data = pData;
	}

	public List<DocumentUi> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentUi> pDocuments) {
		documents = pDocuments;
	}
	
}