package com.asptttoulousenatation.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import static com.asptttoulousenatation.client.resources.ASPTT_ProtoResources.RESOURCE;

public class HeaderPanel extends Composite {

	private Label header;
	private Widget content;
	private Panel panel;
	
	public HeaderPanel(String pHearderText, Widget pContent) {
		panel = new FlowPanel();
		initWidget(panel);
		addStyleName(RESOURCE.css().headerPanel());
		header = new Label(pHearderText);
			content = pContent;
		header.addStyleName(RESOURCE.css().headerPanelHeader());
		content.addStyleName(RESOURCE.css().headerPanelContent());
		panel.add(header);
		panel.add(content);
	}
	
	public HeaderPanel(String pHearderText, String pContent, boolean pIsHtml) {
		panel = new FlowPanel();
		initWidget(panel);
		addStyleName(RESOURCE.css().headerPanel());
		header = new Label(pHearderText);
		if(pIsHtml) {
			content = new HTML(pContent);
		}
		else {
			content = new Label(pContent);
		}
		header.addStyleName(RESOURCE.css().headerPanelHeader());
		content.addStyleName(RESOURCE.css().headerPanelContent());
		panel.add(header);
		panel.add(content);
	}
	
	public void isOdd() {
		header.addStyleName(RESOURCE.css().oddHeader());
		addStyleName(RESOURCE.css().odd());
	}
	
	public void isEven() {
		header.addStyleName(RESOURCE.css().evenHeader());
		addStyleName(RESOURCE.css().even());
	}
	
}