package com.asptttoulousenatation.client.userspace.admin.actu;

import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class ActuCell extends AbstractCell<ActuUi> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			ActuUi pValue, SafeHtmlBuilder pSb) {
		if(pValue == null) {
			return;
		}

		pSb.appendHtmlConstant("<div>");
		pSb.appendEscaped(pValue.getTitle());
		pSb.appendHtmlConstant("</div>");
	}
	
}