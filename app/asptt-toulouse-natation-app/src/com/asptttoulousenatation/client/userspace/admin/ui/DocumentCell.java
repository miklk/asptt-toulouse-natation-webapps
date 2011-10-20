package com.asptttoulousenatation.client.userspace.admin.ui;

import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class DocumentCell extends AbstractCell<DocumentUi> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			DocumentUi pValue, SafeHtmlBuilder pSb) {
		if(pValue == null) {
			return;
		}

		pSb.appendHtmlConstant("<div>");
		pSb.appendEscaped(pValue.getTitle());
		pSb.appendHtmlConstant("</div>");
	}

}
