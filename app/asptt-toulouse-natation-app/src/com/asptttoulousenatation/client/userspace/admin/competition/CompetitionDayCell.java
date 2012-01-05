package com.asptttoulousenatation.client.userspace.admin.competition;

import com.asptttoulousenatation.core.shared.competition.CompetitionDayUi;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class CompetitionDayCell extends AbstractCell<CompetitionDayUi> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			CompetitionDayUi pValue, SafeHtmlBuilder pSb) {
		if (pValue == null) {
			return;
		}

		pSb.appendEscaped("Réunion #" + pValue.getDay());
	}
}