package com.asptttoulousenatation.shared.event;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UiEvents extends HashMap<Date, UiEvent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5233446873222769822L;

	public UiEvents() {
		super();
	}

	public UiEvents(Map<? extends Date, ? extends UiEvent> pArg0) {
		super(pArg0);
	}

	
}
