package com.asptt.core.loading;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

@XmlRootElement
public class LoadingAlbumUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 51159416329748226L;
	
	private String albumId;
	private String couverture;
	private String intitule;
	private String shortIntitule;
	private List<String> photos;
	
	public LoadingAlbumUi() {
		photos = new ArrayList<String>();
	}

	public LoadingAlbumUi(String pAlbumId, String pIntitule, String pCouverture) {
		this();
		albumId = pAlbumId;
		couverture = pCouverture;
		intitule = pIntitule;
		shortIntitule = StringUtils.abbreviate(intitule, 30);
		shortIntitule = StringUtils.rightPad(shortIntitule, 30, " ");
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
	
	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(List<String> pPhotos) {
		photos = pPhotos;
	}

	public void addPhotos(String pPhoto) {
		photos.add(pPhoto);
	}

	public String getShortIntitule() {
		return shortIntitule;
	}

	public void setShortIntitule(String pShortIntitule) {
		shortIntitule = pShortIntitule;
	}
}