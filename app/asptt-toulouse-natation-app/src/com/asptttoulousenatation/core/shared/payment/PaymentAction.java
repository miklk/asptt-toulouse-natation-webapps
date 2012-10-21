package com.asptttoulousenatation.core.shared.payment;

import net.customware.gwt.dispatch.shared.Action;

public class PaymentAction implements Action<PaymentResult> {

	private String ipAddress;
	private String cardType;
	private String cardNumber;
	private String cardExpDate;
	private String cardCCV;
	private String ownerLastName;
	private String ownerFirstName;
	
	private String street;
	private String city;
	private String zipCode;
	
	
	public PaymentAction() {
		
	}

	public PaymentAction(String pIpAddress, String pCardType,
			String pCardNumber, String pCardExpDate, String pCardCCV,
			String pOwnerLastName, String pOwnerFirstName, String pStreet,
			String pCity, String pZipCode) {
		super();
		ipAddress = pIpAddress;
		cardType = pCardType;
		cardNumber = pCardNumber;
		cardExpDate = pCardExpDate;
		cardCCV = pCardCCV;
		ownerLastName = pOwnerLastName;
		ownerFirstName = pOwnerFirstName;
		street = pStreet;
		city = pCity;
		zipCode = pZipCode;
	}



	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String pIpAddress) {
		ipAddress = pIpAddress;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String pCardType) {
		cardType = pCardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String pCardNumber) {
		cardNumber = pCardNumber;
	}

	public String getCardExpDate() {
		return cardExpDate;
	}

	public void setCardExpDate(String pCardExpDate) {
		cardExpDate = pCardExpDate;
	}

	public String getCardCCV() {
		return cardCCV;
	}

	public void setCardCCV(String pCardCCV) {
		cardCCV = pCardCCV;
	}

	public String getOwnerLastName() {
		return ownerLastName;
	}

	public void setOwnerLastName(String pOwnerLastName) {
		ownerLastName = pOwnerLastName;
	}

	public String getOwnerFirstName() {
		return ownerFirstName;
	}

	public void setOwnerFirstName(String pOwnerFirstName) {
		ownerFirstName = pOwnerFirstName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String pStreet) {
		street = pStreet;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String pCity) {
		city = pCity;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String pZipCode) {
		zipCode = pZipCode;
	}
}