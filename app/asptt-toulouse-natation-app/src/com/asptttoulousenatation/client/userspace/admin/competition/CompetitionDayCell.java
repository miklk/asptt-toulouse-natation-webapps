package com.asptttoulousenatation.client.userspace.admin.competition;

import com.asptttoulousenatation.core.shared.competition.CompetitionDayUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class CompetitionDayCell extends AbstractCell<CompetitionDayUi> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			CompetitionDayUi pValue, SafeHtmlBuilder pSb) {
		if (pValue == null) {
			return;
		}

		pSb.appendHtmlConstant("<div>");
		pSb.appendEscaped(pValue.getDay() + " - ");
		pSb.appendEscaped(pValue.getBegin() + " / " + pValue.getEnd() + " - ");
		pSb.appendEscaped("<br />");
		for(UserUi lOfficiel: pValue.getOfficiels()) {
			pSb.appendEscaped(lOfficiel.getUserData().getLastName() + " " + lOfficiel.getUserData().getFirstName() + " / ");
		}
		pSb.appendHtmlConstant("</div>");
	}
}