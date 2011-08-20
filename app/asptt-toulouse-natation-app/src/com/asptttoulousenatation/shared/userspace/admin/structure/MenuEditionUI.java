package com.asptttoulousenatation.shared.userspace.admin.structure;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MenuEditionUI implements IsSerializable {

	private Long id;

	private String title;

	private List<MenuEditionUI> subMenu;
	
	private Long parentId;

	private boolean root;

	public MenuEditionUI() {
		id = 0l;
		parentId = 0l;
		subMenu = new ArrayList<MenuEditionUI>(5);
	}

	public MenuEditionUI(String pTitle) {
		this();
		title = pTitle;
		root = false;
	}

	public MenuEditionUI(String pTitle, boolean pIsRoot) {
		this();
		title = pTitle;
		root = pIsRoot;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public List<MenuEditionUI> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<MenuEditionUI> pSubMenu) {
		subMenu = pSubMenu;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean pRoot) {
		root = pRoot;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long pParentId) {
		parentId = pParentId;
	}
}