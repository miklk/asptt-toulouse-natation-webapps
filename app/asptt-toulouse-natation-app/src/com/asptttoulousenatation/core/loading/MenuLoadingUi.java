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
	
	public MenuLoadingUi() {
		title = StringUtils.EMPTY;
		subMenu = new ArrayList<MenuLoadingUi>();
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
	}
	
	public void addSubMenu(MenuLoadingUi pMenu) {
		subMenu.add(pMenu);
	}
}