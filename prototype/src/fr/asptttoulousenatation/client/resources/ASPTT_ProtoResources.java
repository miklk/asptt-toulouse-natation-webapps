package fr.asptttoulousenatation.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface ASPTT_ProtoResources extends ClientBundle {

	public static final ASPTT_ProtoResources RESOURCE = GWT.create(ASPTT_ProtoResources.class);
	
	public static final ASPTT_ProtoImages IMAGES = GWT.create(ASPTT_ProtoImages.class);
	
	@Source("ASPTT_Proto.css")
	public ASPTT_ProtoCss css();	
}