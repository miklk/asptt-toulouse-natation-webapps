package com.asptt.core.loading;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoadingAlbumsUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7327861427234768860L;
	private List<LoadingAlbumUi> albums;
	
	public LoadingAlbumsUi() {
		albums = new ArrayList<LoadingAlbumUi>(5);
	}

	public List<LoadingAlbumUi> getAlbums() {
		return albums;
	}

	public void setAlbums(List<LoadingAlbumUi> pAlbums) {
		albums = pAlbums;
	}
	
	public void addAlbum(LoadingAlbumUi pAlbum) {
		albums.add(pAlbum);
	}
}
