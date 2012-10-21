package com.asptttoulousenatation.client.subscription;

import java.util.List;

import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.subscription.SubscriptionPriceUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface SubscriptionView extends IsWidget {

	public void setGroupData(List<GroupUi> pGroups);
	
	
	public HasValue<String> getAddressRoad();
	public HasValue<String> getAddressZipCode();
	public HasValue<String> getAddressCity();
	
	public HasClickHandlers getPaymentButton();
	
	
	
	public String getCardType();
	public HasValue<String> getCardNumber();
	public HasValue<String> getCardExpDate();
	public HasValue<String> getCardCCV();
	public HasValue<String> getCardOwnerLastName();
	public HasValue<String> getCardOwnerFirstName();
	
	public HasClickHandlers getPriceButton();
	public void setPrice(SubscriptionPriceUi priceUi);
	public HasClickHandlers getOtherPaymentButton();
	
	public Long getGroup();
	public int getNumberSubscribers();
	
	public HasClickHandlers getPaymentProcessButton();
	public void setPaymentInfo(SubscriptionPriceUi priceUi);
}