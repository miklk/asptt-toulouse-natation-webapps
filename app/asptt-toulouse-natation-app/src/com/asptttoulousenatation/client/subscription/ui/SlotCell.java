package com.asptttoulousenatation.client.subscription.ui;

import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.shared.util.DateUtils;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class SlotCell extends AbstractCell<SlotUi> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			SlotUi pValue, SafeHtmlBuilder pSb) {
		pSb.appendHtmlConstant("<div>");
		pSb.appendEscaped(pValue.getSwimmingPool());
		pSb.appendHtmlConstant("<br />");
		pSb.appendEscaped(pValue.getDayOfWeek()).appendEscaped("-");
		//Hour:minutes
		int[] lHourMinutes = DateUtils.getHour(pValue.getBegin());
		pSb.appendEscaped(lHourMinutes[0] + ":" + lHourMinutes[1]);
		pSb.appendEscaped("~");
		lHourMinutes = DateUtils.getHour(pValue.getEnd());
		pSb.appendEscaped(lHourMinutes[0] + ":" + lHourMinutes[1]);
		pSb.appendHtmlConstant("</div>");
	}
}
