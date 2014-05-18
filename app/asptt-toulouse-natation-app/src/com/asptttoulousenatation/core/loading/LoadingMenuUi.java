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
	
	public LoadingMenuUi() {
		title = StringUtils.EMPTY;
		subMenu = new ArrayList<LoadingMenuUi>();
		hasSubMenu = false;
	}
	
	public LoadingMenuUi(String pTitle) {
		this();
		title = pTitle;
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
	
}