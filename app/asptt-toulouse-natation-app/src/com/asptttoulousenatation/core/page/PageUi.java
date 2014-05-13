package com.asptttoulousenatation.core.page;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PageUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7248929381942539859L;
	private String content;
	
	public PageUi() {
		
	}

	public PageUi(String pContent) {
		super();
		content = pContent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String pContent) {
		content = pContent;
	}
}