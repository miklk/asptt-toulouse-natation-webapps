package com.asptttoulousenatation.core.loading;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoadingAlbumUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 51159416329748226L;
	
	private String albumId;
	private String couverture;
	private String intitule;
	
	public LoadingAlbumUi() {
		
	}

	public LoadingAlbumUi(String pAlbumId, String pIntitule, String pCouverture) {
		this();
		albumId = pAlbumId;
		couverture = pCouverture;
		intitule = pIntitule;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String pAlbumId) {
		albumId = pAlbumId;
	}

	public String getCouverture() {
		return couverture;
	}

	public void setCouverture(String pCouverture) {
		couverture = pCouverture;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String pIntitule) {
		intitule = pIntitule;
	}
	
}
