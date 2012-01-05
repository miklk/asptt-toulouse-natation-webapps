package com.asptttoulousenatation.client.userspace.admin.club.slot;

import com.asptttoulousenatation.client.util.Utils;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class SlotCell extends AbstractCell<SlotUi> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			SlotUi pValue, SafeHtmlBuilder pSb) {
		if (pValue == null) {
			return;
		}

		pSb.appendHtmlConstant("<div>");
		pSb.appendEscaped(pValue.getDayOfWeek() + " - ");
		pSb.appendEscaped(Utils.getTimeAsString(pValue.getBegin()) + " - "
				+ Utils.getTimeAsString(pValue.getEnd()) + " - ");
		if (pValue.getGroup() != null) {
			pSb.appendEscaped(pValue.getGroup().getTitle() + " - ");
		}
		pSb.appendEscaped(pValue.getSwimmingPool());
		pSb.appendHtmlConstant("</div>");
	}
}