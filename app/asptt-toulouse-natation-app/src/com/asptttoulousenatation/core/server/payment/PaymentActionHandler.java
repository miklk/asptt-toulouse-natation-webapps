package com.asptttoulousenatation.core.server.payment;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.shared.payment.PaymentAction;
import com.asptttoulousenatation.core.shared.payment.PaymentResult;
import com.paypal.sdk.core.nvp.NVPDecoder;
import com.paypal.sdk.core.nvp.NVPEncoder;
import com.paypal.sdk.exceptions.PayPalException;
import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.profiles.ProfileFactory;
import com.paypal.sdk.services.NVPCallerServices;
import com.paypal.sdk.util.ResponseBuilder;

public class PaymentActionHandler implements
		ActionHandler<PaymentAction, PaymentResult> {

	public PaymentResult execute(PaymentAction pAction,
			ExecutionContext pContext) throws DispatchException {
		final PaymentResult lResult = new PaymentResult();
		NVPEncoder encoder = new NVPEncoder();

		encoder.add("METHOD", "DoDirectPayment");
		encoder.add("PAYMENTACTION", "Sale");
		encoder.add("IPADDRESS", pAction.getIpAddress());

		encoder.add("CREDITCARDTYPE", pAction.getCardType());
		encoder.add("ACCT", pAction.getCardNumber());
		encoder.add("EXPDATE", pAction.getCardExpDate());
		encoder.add("CVV2", pAction.getCardCCV());
		encoder.add("FIRSTNAME", pAction.getOwnerFirstName());
		encoder.add("LASTNAME", pAction.getOwnerLastName());

		encoder.add("STREET", pAction.getStreet());
		encoder.add("CITY", pAction.getCity());
		encoder.add("STATE", "FR");
		encoder.add("COUNTRYCODE", "FR");
		encoder.add("ZIP", pAction.getZipCode());

		encoder.add("AMT", "250");
		encoder.add("CURRENCYCODE", "EUR");

		try {
			// encode method will encode the name and value and form NVP string
			// for the request
			String NVPString = encoder.encode();

			NVPCallerServices lCallerServices = new NVPCallerServices();
			APIProfile lProfile = ProfileFactory.createSignatureAPIProfile();
			lProfile.setAPIUsername("asptt_1337287226_biz_api1.asptt-toulouse-natation.com");
			lProfile.setAPIPassword("1337287248");
			lProfile.setSignature("AbYjeepx8KvIxQGGXbe5XeQ8dntrAFSXV539xCOySUCm4fhcR25dLwzy");
			lProfile.setEnvironment("sandbox");
			lProfile.setSubject("");
			lCallerServices.setAPIProfile(lProfile);
			// call method will send the request to the server and return the
			// response NVPString
			String ppresponse = (String) lCallerServices.call(NVPString);
			// NVPDecoder object is created
			NVPDecoder resultValues = new NVPDecoder();
			// decode method of NVPDecoder will parse the request and decode the
			// name and value pair
			resultValues.decode(ppresponse);

			String header1 = "Do Direct Payment";
			String header2 = "Thank you for your payment!";
			String resp = ResponseBuilder.BuildResponse(resultValues, header1, header2);
			lResult.setMessage(resp);
		} catch (PayPalException e) {
			lResult.setMessage(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			lResult.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return lResult;
	}

	public Class<PaymentAction> getActionType() {
		return PaymentAction.class;
	}

	public void rollback(PaymentAction pAction, PaymentResult pResult,
			ExecutionContext pContext) throws DispatchException {
	}
}