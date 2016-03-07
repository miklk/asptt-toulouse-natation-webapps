package com.asptttoulousenatation.core.actualite;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class ActualitePublishParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private String beginAsString;
	private String endAsString;
	private String image;
	private String content;
	private boolean facebook;
	private boolean draft;
	private String user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public DateTime getBegin() {
		return DateTime.parse(beginAsString, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
	}
	public DateTime getEnd() {
		return DateTime.parse(endAsString, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isFacebook() {
		return facebook;
	}
	public void setFacebook(boolean facebook) {
		this.facebook = facebook;
	}
	public boolean isDraft() {
		return draft;
	}
	public void setDraft(boolean draft) {
		this.draft = draft;
	}
	public String getBeginAsString() {
		return beginAsString;
	}
	public void setBeginAsString(String beginAsString) {
		this.beginAsString = beginAsString;
	}
	public String getEndAsString() {
		return endAsString;
	}
	public void setEndAsString(String endAsString) {
		this.endAsString = endAsString;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
}
