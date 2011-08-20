package com.asptttoulousenatation.client.userspace.admin.user;

import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class UserCell extends AbstractCell<UserUi> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			UserUi pValue, SafeHtmlBuilder pSb) {
		if(pValue == null) {
			return;
		}

		pSb.appendHtmlConstant("<div>");
		pSb.appendEscaped(pValue.getEmailAddress());
		pSb.appendHtmlConstant("</div>");
	}
}