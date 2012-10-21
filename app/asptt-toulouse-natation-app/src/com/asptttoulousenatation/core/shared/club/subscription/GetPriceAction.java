package com.asptttoulousenatation.core.shared.club.subscription;


import net.customware.gwt.dispatch.shared.Action;

public class GetPriceAction implements Action<GetPriceResult> {

	private Long group;
	private int subscribers;
	
	public GetPriceAction() {
		
	}

	public GetPriceAction(Long pGroup, int pSubscribers) {
		super();
		group = pGroup;
		subscribers = pSubscribers;
	}

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long pGroup) {
		group = pGroup;
	}

	public int getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(int pSubscribers) {
		subscribers = pSubscribers;
	}
}
