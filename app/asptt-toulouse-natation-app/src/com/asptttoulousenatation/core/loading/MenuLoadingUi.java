package com.asptttoulousenatation.core.loading;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

@XmlRootElement
public class MenuLoadingUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6045082583153988078L;

	private String title;
	
	private List<MenuLoadingUi> subMenu;
	
	private boolean hasSubMenu;
	
	public MenuLoadingUi() {
		title = StringUtils.EMPTY;
		subMenu = new ArrayList<MenuLoadingUi>();
		hasSubMenu = false;
	}
	
	public MenuLoadingUi(String pTitle) {
		this();
		title = pTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public List<MenuLoadingUi> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<MenuLoadingUi> pSubMenu) {
		subMenu = pSubMenu;
		hasSubMenu = !subMenu.isEmpty();
	}
	
	public void addSubMenu(MenuLoadingUi pMenu) {
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