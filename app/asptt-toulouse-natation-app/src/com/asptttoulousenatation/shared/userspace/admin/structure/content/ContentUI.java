package com.asptttoulousenatation.shared.userspace.admin.structure.content;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContentUI implements IsSerializable {

	private long id;
	private String summary;
	private byte[] data;
	private ContentDataKindEnum kind;
	
	public ContentUI() {
		
	}

	public ContentUI(long pId, String pSummary, byte[] pData,
			ContentDataKindEnum pKind) {
		id = pId;
		summary = pSummary;
		data = pData;
		kind = pKind;
	}

	public long getId() {
		return id;
	}

	public void setId(long pId) {
		id = pId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String pSummary) {
		summary = pSummary;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] pData) {
		data = pData;
	}

	public ContentDataKindEnum getKind() {
		return kind;
	}

	public void setKind(ContentDataKindEnum pKind) {
		kind = pKind;
	}
}