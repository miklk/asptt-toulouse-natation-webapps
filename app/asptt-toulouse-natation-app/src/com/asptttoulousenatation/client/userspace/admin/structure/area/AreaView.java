package com.asptttoulousenatation.client.userspace.admin.structure.area;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface AreaView extends IsWidget {
	public HasValue<String> getMenuTitle();
	public HasValue<String> getSummary();
	public String getContent();
	public HasClickHandlers getUpdateButton();
	public HasClickHandlers getDeleteButton();
	public Long getContentId();
	public Long getMenuId();
	
	public HasClickHandlers getDocumentUpdateButton();
	public HasClickHandlers getDocumentDeleteButton();
	public Long getDocumentId();
	public HasValue<String> getDocumentTitle();
	public HasValue<String> getDocumentSummary();
	
	//Area edition
	public HasClickHandlers getAreaUpdateButton();
	public HasClickHandlers getAreaDeleteButton();
	public HasValue<String> getAreaTitle();
	public Short getAreaOrder();
	
	//Menu creation
	public HasClickHandlers getMenuCreationButton();
	public HasValue<String> getMenuCreationTitle();
	public HasValue<String> getMenuCreationSummary();
	public String getMenuCreationContent();

	public Long getParentMenuId();
	
	public void hideMenuCreationPopup();
	
	//Sub menu creation
	public HasClickHandlers getSubMenuCreationButton();
	public HasValue<String> getSubMenuCreationTitle();
	
}