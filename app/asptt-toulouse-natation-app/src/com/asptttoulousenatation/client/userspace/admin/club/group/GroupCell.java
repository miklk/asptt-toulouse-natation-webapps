package com.asptttoulousenatation.client.userspace.admin.club.group;

import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class GroupCell extends AbstractCell<GroupUi> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			GroupUi pValue, SafeHtmlBuilder pSb) {
		if(pValue == null) {
			return;
		}

		pSb.appendHtmlConstant("<div>");
		pSb.appendEscaped(pValue.getTitle());
		pSb.appendHtmlConstant("</div>");
	}
}