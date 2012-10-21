/*
 * Copyright 2005 PayPal, Inc. All Rights Reserved.
 */

package com.paypal.sdk.core.nvp;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.paypal.sdk.core.APICallerBase;
import com.paypal.sdk.core.Constants;
import com.paypal.sdk.exceptions.FatalException;
import com.paypal.sdk.exceptions.PayPalException;
import com.paypal.sdk.exceptions.TransactionException;
import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.profiles.CertificateAPIProfile;
import com.paypal.sdk.profiles.PermissionAPIProfile;
import com.paypal.sdk.profiles.SignatureAPIProfile;
import com.paypal.sdk.profiles.UniPayAPIProfile;
import com.paypal.sdk.util.MessageResources;
import com.paypal.sdk.util.Util;

/**
 * NVP API caller class.
 */
public class NVPAPICaller extends APICallerBase
{
	private static Log log = LogFactory.getLog(NVPAPICaller.class);

	/**
	 * PayPal NVP endopoint URL
	 */
	private String endpoint;

	/**
	 * PayPal NVP security header
	 */
	private String header;

	/**
	 * Maximum number of retries
	 */
	private int maximumRetries;

	/**
	 * PayPal NVP service name
	 */
	private static final QName service = new QName("PayPalAPI");
	/**
	 * Permission Header
	 */
	private String permissionHeader;

	static
	{
		readEndpoints(Constants.NVP_ENDPOINTS);
	}

	/**
	 * This method validates the API profile for a NVP connection.
	 *
	 * @param _profile          the profile object set by the merchant or from samples
	 * @return                  void
	 * @throws PayPalException  if an exception occurs.
	 */
	protected void validateProfile(APIProfile _profile) throws PayPalException
	{
		List errors = new ArrayList();
		if (Util.isEmpty(_profile.getEnvironment()))
		{
			errors.add(MessageResources.getMessage("API_ENVIRONMENT_EMPTY"));
		}
		if (_profile instanceof CertificateAPIProfile)
		{
			if (Util.isEmpty(_profile.getCertificateFile()))
			{
				errors.add(MessageResources.getMessage("API_CERTIFICATE_FILE_EMPTY"));
			}
			else
			{
				File file = new File(_profile.getCertificateFile());
				if (!file.exists())
				{
					errors.add(MessageResources.getMessage("API_CERTIFICATE_FILE_MISSING"));
				}
			}
			if ((_profile.getPrivateKeyPassword() == null))
			{
				errors.add(MessageResources.getMessage("API_PRIVATE_KEY_PASSWORD_EMPTY"));
			}
		}
		if (_profile instanceof UniPayAPIProfile)
		{
			if (Util.isEmpty(_profile.getFirstPartyEmail() ))
			{
				errors.add("email is empty");
			}

		}
		if (_profile instanceof SignatureAPIProfile)
		{
			if (Util.isEmpty(_profile.getAPIUsername()))
			{
				errors.add(MessageResources.getMessage("API_APIUSERNAME_EMPTY"));
			}
			if (Util.isEmpty(_profile.getAPIPassword()))
			{
				errors.add(MessageResources.getMessage("API_APIPASSWORD_EMPTY"));
			}
			if (Util.isEmpty(_profile.getSignature()))
			{
				errors.add(MessageResources.getMessage("API_SIGNATURE_EMPTY"));
			}

		}
		if(_profile instanceof PermissionAPIProfile){
			if (Util.isEmpty(_profile.getOauth_Signature()))
			{
				errors.add(MessageResources.getMessage("API_PERMISSION_SIGNATURE_EMPTY"));
			}
			if (Util.isEmpty(_profile.getOauth_Timestamp()))
			{
				errors.add(MessageResources.getMessage("API_PERMISSION_TIMESTAMP_EMPTY"));
			}
			if (Util.isEmpty(_profile.getOauth_Token()))
			{
				errors.add(MessageResources.getMessage("API_PERMISSION_TOKEN_EMPTY"));
			}
		}
		if (!errors.isEmpty())
		{
			StringBuffer msg = new StringBuffer(MessageResources.getMessage("PROFILE_INVALID"));
			Iterator iterator = errors.iterator();
			while (iterator.hasNext())
			{
				msg.append("\n" + ((String) iterator.next()));
			}
			throw new TransactionException(msg.toString());
		}
	}

	/**
	 * This method creates a SSL connection to URL taken from the paypal-endpoints.xml. It takes
	 * appropriate URL based on the type of authentication preferred Certificate or 3 Token method.
	 *
	 * @param _profile          the profile object set by the merchant or from samples
	 * @return                  void
	 * @throws PayPalException  if an exception occurs.
	 */
	public final synchronized void setupConnection(APIProfile _profile) throws PayPalException
	{
		super.setupConnection(_profile);
		// Retrieve the endpoint URL
		String endpointUrl = null;
        boolean stage = Util.isStage(_profile.getEnvironment());
		if ( stage ) {
			endpointUrl="https://api."+_profile.getEnvironment()+".paypal.com/nvp";
		} else {
			endpointUrl=this.getEndpointUrl(_profile, service);
		}
		if ( (endpointUrl == null) & (Util.isEmpty(endpointUrl)))
			throw new TransactionException(MessageFormat.format(MessageResources
					.getMessage("ENDPOINT_NOT_FOUND"), new Object[] {
				_profile.getEnvironment(), service.getLocalPart()}));
		try
		{
			endpoint = endpointUrl;
		}
		catch (Exception e)
		{
			throw new TransactionException(MessageFormat.format(MessageResources
					.getMessage("ENDPOINT_INVALID"), new Object[] {endpointUrl}));
		}

		if (log.isDebugEnabled())
		{
			log.debug(MessageFormat.format(MessageResources.getMessage("CONNECTION_OPEN"),
					new Object[] {service.getLocalPart(), endpointUrl}));
		}
		NVPEncoder encoder=null;
		if(_profile instanceof PermissionAPIProfile){
			StringBuffer authString=new StringBuffer();
	        authString.append("token="+_profile.getOauth_Token());
	        authString.append(",");
	        authString.append("signature="+_profile.getOauth_Signature());
	        authString.append(",");
	        authString.append("timestamp="+_profile.getOauth_Timestamp());

	        permissionHeader=authString.toString();
	        encoder= new NVPEncoder();
		}else{
			// Create the security header
			encoder = setCorrectProfile(_profile);
		}

		//encoder.add("VERSION", Constants.DEFAULT_API_VERSION);
		encoder.add("SOURCE", Constants.API_SOURCE);
		header = encoder.encode();

		maximumRetries = _profile.getMaximumRetries();

		// Set the HTTPS protocol handler
	} // setupConnection

	private NVPEncoder setCorrectProfile(APIProfile _profile) {
		NVPEncoder encoder = new NVPEncoder();
		if ((_profile instanceof UniPayAPIProfile) && !Util.isEmpty(_profile.getFirstPartyEmail()))
		{
			encoder.add("SUBJECT", _profile.getFirstPartyEmail());
			return encoder;
		}
		if (!Util.isEmpty(_profile.getAPIUsername()))
		{
			encoder.add("USER", _profile.getAPIUsername());
		}
		if (!Util.isEmpty(_profile.getAPIPassword()))
		{
			encoder.add("PWD", _profile.getAPIPassword());
		}
		if (!Util.isEmpty(_profile.getSubject()))
		{
			encoder.add("SUBJECT", _profile.getSubject());
		}
		if ((_profile instanceof SignatureAPIProfile) && !Util.isEmpty(_profile.getSignature()))
		{
			encoder.add("SIGNATURE", _profile.getSignature());
		}
		return encoder;
	}

	/**
	 * This method invokes all the API calls depending on the request string sent in as parameter
	 * in this method which has to be in NVP format.
	 *
	 * @param payload              Payload string
	 * @return String              Response String from the server in the NVP format.
	 * @exception PayPalException  If exception occurs
	 */
	public final String call(String payload) throws PayPalException
	{
		try
		{
			URLFetchService lUrlFetchService = URLFetchServiceFactory.getURLFetchService();

			StringBuffer request = new StringBuffer(payload);
			request.append("&");
        	request.append(header);
        	
        	NVPDecoder decodeReq=new NVPDecoder();
        	decodeReq.decode(request.toString());
        	Map map=decodeReq.getMap();
        	if(!map.containsKey("VERSION")){
        		NVPEncoder encoderReq=new NVPEncoder();
            	encoderReq.add("VERSION",Constants.DEFAULT_API_VERSION);
            	request.append("&");
            	request.append(encoderReq.encode());
        	}
        	
			if (log.isInfoEnabled())
			{
				NVPDecoder decoder = new NVPDecoder();
				String requestmask = request.toString();
				decoder.decode(requestmask);
				if (!Util.isEmpty(decoder.get("PWD")))
				{
					requestmask = requestmask.replaceAll(decoder.get("PWD"), "******");
				}
				if (!Util.isEmpty(decoder.get("SIGNATURE")))
				{
					requestmask = requestmask.replaceAll(decoder.get("SIGNATURE"), "**********");
				}
				if (!Util.isEmpty(decoder.get("CVV2")))
				{
					requestmask = requestmask.replaceAll(decoder.get("CVV2"), "****");
				}
				if (!Util.isEmpty(decoder.get("ACCT")))
				{
					requestmask = requestmask.replaceAll(decoder.get("ACCT"), "****************");
				}
				log.info(MessageFormat.format(MessageResources.getMessage("TRANSACTION_SENT"),
						new Object[] {requestmask}));
			}

			URL lUrl = new URL(endpoint + "/?" + request);
			HTTPRequest lHttpRequest = new HTTPRequest(lUrl, HTTPMethod.POST);

			Date startTime = new Date();
			//Permission Header added in http Request
			if(permissionHeader != null && permissionHeader.length()>0){
				lHttpRequest.addHeader(new HTTPHeader("X_PP_AUTHORIZATION",permissionHeader));
			}
			HTTPResponse response = lUrlFetchService.fetch(lHttpRequest);
			int result = response.getResponseCode();
			if ( result != 200 ) {
				throw new FatalException("HTTP Error code " + result + " received, transaction not submitted");
			}
			
			String asString = new String(response.getContent());

			// Log the transaction result
			if (log.isInfoEnabled())
			{
				Date endTime = new Date();
				log.info(MessageFormat.format(
						MessageResources.getMessage("TRANSACTION_RESULT"), new Object[] {
							response, String.valueOf(result),
							new Long(endTime.getTime() - startTime.getTime())}));
			}
			return asString;
		}
		catch (Exception e)
		{
			throw new FatalException(MessageResources.getMessage("TRANSACTION_FAILED"), e);
		}
	} // call


}