package com.asptttoulousenatation.core.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface AutohitsCoreResource extends ClientBundle {

	public static final AutohitsCoreResource RESOURCE = GWT.create(AutohitsCoreResource.class);
	
	@Source("AutohitsCore.css")
	public AutohitsCoreCss css();
	
	public static final CoreImageResources IMAGES = GWT.create(CoreImageResources.class);
	
}
