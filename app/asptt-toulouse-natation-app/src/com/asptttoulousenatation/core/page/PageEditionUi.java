package com.asptttoulousenatation.core.page;

import java.io.Serializable;

import javax.jdo.annotations.Persistent;

public class PageEditionUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private Integer order;
	private String content;
	private Boolean divider;
	private Boolean alone;
	private String identifier;
	private Boolean display;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getDivider() {
		return divider;
	}
	public void setDivider(Boolean divider) {
		this.divider = divider;
	}
	public Boolean getAlone() {
		return alone;
	}
	public void setAlone(Boolean alone) {
		this.alone = alone;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Boolean getDisplay() {
		return display;
	}
	public void setDisplay(Boolean display) {
		this.display = display;
	}
}