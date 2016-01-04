package com.asptttoulousenatation.core.actualite;

import java.io.Serializable;

import org.joda.time.DateTime;

public class ActualitePublishParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private DateTime begin;
	private DateTime end;
	private String image;
	private String content;
	private boolean facebook;
	private boolean draft;
	
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
		return begin;
	}
	public void setBegin(DateTime begin) {
		this.begin = begin;
	}
	public DateTime getEnd() {
		return end;
	}
	public void setEnd(DateTime end) {
		this.end = end;
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
	
}
