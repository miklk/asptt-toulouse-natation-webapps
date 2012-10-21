/*
 * Copyright 2005 PayPal, Inc. All Rights Reserved.
 */
package com.paypal.sdk.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class Util
{
    private static Log log = LogFactory.getLog(Util.class);

    public static boolean isStage(String name ) {
	    	boolean stage=false;
	     	if (name.indexOf("stage") >= 0 )
	        	stage=true;
	     	return stage;
    }
    public static boolean isEmpty(String s)
    {
        return s == null || s.length() == 0;
    }
    public static float round(float Rval, int Rpl) {
		float p = (float)Math.pow(10,Rpl);
		Rval = Rval * p;
		float tmp = Math.round(Rval);
		return (float)tmp/p;
    }
}
