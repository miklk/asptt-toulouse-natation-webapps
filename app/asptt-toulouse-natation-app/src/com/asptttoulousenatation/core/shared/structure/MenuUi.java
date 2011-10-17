package com.asptttoulousenatation.core.shared.structure;

import java.util.List;

import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.MenuEditionUI;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentUI;
import com.google.gwt.user.client.rpc.IsSerializable;

public class MenuUi implements IsSerializable {
	
	private Long id;
	
	private String menuKey;
	
	private String title;

	private List<MenuEditionUI> subMenu;
	
	private Long parentId;

	private boolean root;
	
	private List<ContentUI> contentSet;
	
	private boolean shortcut;
	
	private List<DocumentUi> documentSet;
	
	public MenuUi() {
		id = 0l;
	}

	public MenuUi(String pMenuKey, String pTitle, List<MenuEditionUI> pSubMenu, Long pParentId,
			boolean pRoot, boolean pShortcut) {
		this();
		menuKey = pMenuKey;
		title = pTitle;
		subMenu = pSubMenu;
		parentId = pParentId;
		root = pRoot;
		shortcut = pShortcut;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String pMenuKey) {
		menuKey = pMenuKey;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long pParentId) {
		parentId = pParentId;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean pRoot) {
		root = pRoot;
	}

	public List<ContentUI> getContentSet() {
		return contentSet;
	}

	public void setContentSet(List<ContentUI> pContentSet) {
		contentSet = pContentSet;
	}

	public boolean isShortcut() {
		return shortcut;
	}

	public void setShortcut(boolean pShortcut) {
		shortcut = pShortcut;
	}

	public List<DocumentUi> getDocumentSet() {
		return documentSet;
	}

	public void setDocumentSet(List<DocumentUi> pDocumentSet) {
		documentSet = pDocumentSet;
	}
	
}