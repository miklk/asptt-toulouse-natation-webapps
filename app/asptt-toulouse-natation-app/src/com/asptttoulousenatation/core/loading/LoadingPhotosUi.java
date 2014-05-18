package com.asptttoulousenatation.core.loading;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoadingPhotosUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7413851056425534031L;
	private String imageUrl;
	
	public LoadingPhotosUi() {
	}
	
	public LoadingPhotosUi(String pImageUrl) {
		imageUrl = pImageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String pImageUrl) {
		imageUrl = pImageUrl;
	}
	
}