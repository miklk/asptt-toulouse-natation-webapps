package com.asptttoulousenatation.core.shared.structure;

import net.customware.gwt.dispatch.shared.Result;

public class LoadContentResult implements Result {

	private byte[] data;
	
	public LoadContentResult() {
		
	}

	public LoadContentResult(byte[] pData) {
		super();
		data = pData;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] pData) {
		data = pData;
	}
}