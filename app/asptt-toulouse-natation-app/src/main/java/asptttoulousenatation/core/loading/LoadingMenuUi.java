package com.asptttoulousenatation.core.loading;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

@XmlRootElement
public class LoadingMenuUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6045082583153988078L;

	private String title;
	
	private List<LoadingMenuUi> subMenu;
	
	private boolean hasSubMenu;
	
	private boolean divider;
	private boolean alone;
	private String identifier;
	
	public LoadingMenuUi() {
		title = StringUtils.EMPTY;
		subMenu = new ArrayList<LoadingMenuUi>();
		hasSubMenu = false;
		divider = false;
		alone = false;
		identifier = StringUtils.EMPTY;
	}
	
	public LoadingMenuUi(String pTitle, boolean pDivider, boolean pAlone, String pIdentifier) {
		this();
		title = pTitle;
		divider = pDivider;
		alone = pAlone;
		if(StringUtils.isBlank(pIdentifier)) {
			identifier = pTitle;
		} else {
			identifier = pIdentifier;
		}
	}
	
	public LoadingMenuUi(String pTitle) {
		this(pTitle, false, false, pTitle);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public List<LoadingMenuUi> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<LoadingMenuUi> pSubMenu) {
		subMenu = pSubMenu;
		hasSubMenu = !subMenu.isEmpty();
	}
	
	public void addSubMenu(LoadingMenuUi pMenu) {
		hasSubMenu = true;
		subMenu.add(pMenu);
	}

	public boolean isHasSubMenu() {
		return hasSubMenu;
	}

	public void setHasSubMenu(boolean pHasSubMenu) {
		hasSubMenu = pHasSubMenu;
	}

	public boolean isDivider() {
		return divider;
	}

	public void setDivider(boolean pDivider) {
		divider = pDivider;
	}

	public boolean isAlone() {
		return alone;
	}

	public void setAlone(boolean pAlone) {
		alone = pAlone;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String pIdentifier) {
		identifier = pIdentifier;
	}
}