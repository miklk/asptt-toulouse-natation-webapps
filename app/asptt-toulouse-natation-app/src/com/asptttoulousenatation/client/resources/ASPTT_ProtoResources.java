package com.asptttoulousenatation.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ASPTT_ProtoResources extends ClientBundle {

	public static final ASPTT_ProtoResources RESOURCE = GWT.create(ASPTT_ProtoResources.class);
	
	public static final ASPTT_ProtoImages IMAGES = GWT.create(ASPTT_ProtoImages.class);
	
	@Source("ASPTT_Proto.css")
	public ASPTT_ProtoCss css();	
	
	@Source("images/headerBackgroundArrondi.png")
	public ImageResource headerBackground();
	
	@Source("images/degrade_3.png")
	public ImageResource degrade3();
	
	@Source("images/lignes_grand.jpg")
	public ImageResource ligneGrande();
}