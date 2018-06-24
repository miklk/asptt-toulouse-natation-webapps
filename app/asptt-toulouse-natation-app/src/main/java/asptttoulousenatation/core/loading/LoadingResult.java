package com.asptttoulousenatation.core.loading;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.shared.actu.ActuUi;

@XmlRootElement
public class LoadingResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7303288336521536060L;
	private List<LoadingMenuUi> menus;
	private List<ActuUi> actualites;
	
	public LoadingResult() {
		menus = new ArrayList<LoadingMenuUi>();
		actualites = new ArrayList<ActuUi>();
	}

	public List<LoadingMenuUi> getMenus() {
		return menus;
	}

	public void setMenus(List<LoadingMenuUi> pMenus) {
		menus = pMenus;
	}

	public List<ActuUi> getActualites() {
		return actualites;
	}

	public void setActualites(List<ActuUi> pActualites) {
		actualites = pActualites;
	}
	
	public void addMenu(LoadingMenuUi pMenu) {
		menus.add(pMenu);
	}
	
	public void addActualite(ActuUi pActu) {
		actualites.add(pActu);
	}
}