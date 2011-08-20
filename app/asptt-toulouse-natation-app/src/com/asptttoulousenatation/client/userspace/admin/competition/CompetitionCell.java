package com.asptttoulousenatation.client.userspace.admin.competition;

import com.asptttoulousenatation.core.shared.competition.CompetitionUi;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class CompetitionCell extends AbstractCell<CompetitionUi> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			CompetitionUi pValue, SafeHtmlBuilder pSb) {
		if (pValue == null) {
			return;
		}

		pSb.appendHtmlConstant("<div>");
		pSb.appendEscaped(pValue.getTitle());
		pSb.appendHtmlConstant("</div>");
	}
}