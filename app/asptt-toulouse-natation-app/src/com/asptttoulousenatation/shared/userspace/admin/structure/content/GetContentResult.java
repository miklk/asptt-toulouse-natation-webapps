package com.asptttoulousenatation.shared.userspace.admin.structure.content;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class GetContentResult implements Result {

	private List<ContentUI> contents;
	
	public GetContentResult() {
		
	}

	public GetContentResult(List<ContentUI> pContents) {
		contents = pContents;
	}

	public List<ContentUI> getContents() {
		return contents;
	}

	public void setContents(List<ContentUI> pContents) {
		contents = pContents;
	}
}