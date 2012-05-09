package com.asptttoulousenatation.client.userspace.admin.structure.menu.ui;

import java.util.List;

import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class MenuParentCell extends CompositeCell<MenuUi> {
	
	public MenuParentCell(List<HasCell<MenuUi, ?>> pHasCells) {
		super(pHasCells);
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			MenuUi pValue, SafeHtmlBuilder pSb) {
		pSb.appendHtmlConstant("<table><tbody><tr>");
		super.render(pContext, pValue, pSb);
		pSb.appendHtmlConstant("</tr></tbody></table>");
	}
	

	@Override
	protected Element getContainerElement(Element pParent) {
		return pParent.getFirstChildElement().getFirstChildElement().getFirstChildElement();
	}

	@Override
	protected <X> void render(com.google.gwt.cell.client.Cell.Context pContext,
			MenuUi pValue, SafeHtmlBuilder pSb, HasCell<MenuUi, X> pHasCell) {
		Cell<X> lCell = pHasCell.getCell();
		pSb.appendHtmlConstant("<td>");
		lCell.render(pContext, pHasCell.getValue(pValue), pSb);
		pSb.appendHtmlConstant("</td>");
	}
}