package com.asptttoulousenatation.client.userspace.admin.structure.menu.ui;

import com.asptttoulousenatation.client.resources.ASPTT_ProtoResources;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class MenuEditionCell extends ActionCell<MenuUi> {

	private ImageResourceCell cell =  new ImageResourceCell();
	
	public MenuEditionCell(
			SafeHtml pMessage,
			com.google.gwt.cell.client.ActionCell.Delegate<MenuUi> pDelegate) {
		super(pMessage, pDelegate);
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context pContext,
			MenuUi pValue, SafeHtmlBuilder pSb) {
		pSb.appendHtmlConstant("<span style=\"cursor: pointer\">");
		cell.render(pContext, ASPTT_ProtoResources.IMAGES.iconEdit(), pSb);
		pSb.appendHtmlConstant("</span>");
	}
}