package com.asptttoulousenatation.client.subscription.ui;

import java.util.List;

import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class SlotSelectionCell extends CompositeCell<SlotUi> {

	public SlotSelectionCell(List<HasCell<SlotUi, ?>> pHasCells) {
		super(pHasCells);
	}
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			SlotUi pValue, SafeHtmlBuilder pSb) {
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
			SlotUi pValue, SafeHtmlBuilder pSb, HasCell<SlotUi, X> pHasCell) {
		Cell<X> lCell = pHasCell.getCell();
		pSb.appendHtmlConstant("<td>");
		lCell.render(pContext, pHasCell.getValue(pValue), pSb);
		pSb.appendHtmlConstant("</td>");
	}
}
