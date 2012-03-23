package com.asptttoulousenatation.shared.event;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UiEvent implements IsSerializable {

	private Date eventDate;
	private String eventTitle;
	
	public UiEvent() {
		
	}

	public UiEvent(Date pEventDate, String pEventTitle) {
		this();
		eventDate = pEventDate;
		eventTitle = pEventTitle;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date pEventDate) {
		eventDate = pEventDate;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String pEventTitle) {
		eventTitle = pEventTitle;
	}
}