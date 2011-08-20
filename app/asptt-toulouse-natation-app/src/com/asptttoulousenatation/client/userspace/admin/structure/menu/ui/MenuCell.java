package com.asptttoulousenatation.client.userspace.admin.structure.menu.ui;

import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class MenuCell extends AbstractCell<MenuUi> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			MenuUi pValue, SafeHtmlBuilder pSb) {
		if(pValue == null) {
			return;
		}

		pSb.appendHtmlConstant("<div>");
		pSb.appendEscaped(pValue.getTitle());
		pSb.appendHtmlConstant("</div>");
	}
}