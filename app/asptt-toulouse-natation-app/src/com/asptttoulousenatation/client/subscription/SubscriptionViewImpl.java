package com.asptttoulousenatation.client.subscription;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SubscriptionViewImpl extends Composite implements SubscriptionView {

	private VerticalPanel panel;
	
	public SubscriptionViewImpl() {
		panel = new VerticalPanel();
		initWidget(panel);

		buildEtatCivil();
	}
	
	private void buildEtatCivil() {
		CaptionPanel lPanel = new CaptionPanel();
		FlexTable lInnerPanel = new FlexTable();
		int lRowIndex = 0;
		
		
		lInnerPanel.setWidget(lRowIndex, 0, new Label("Etat civil"));
		RadioButton lMan = new RadioButton("etatcivil", "Mr");
		lInnerPanel.setWidget(lRowIndex, 1, lMan);
		RadioButton lWoman = new RadioButton("etatcivil", "Mme");
		lInnerPanel.setWidget(lRowIndex, 2, lWoman);
		RadioButton lGirl = new RadioButton("etatcivil", "Mlle");
		lInnerPanel.setWidget(lRowIndex, 3, lGirl);
		lPanel.add(lInnerPanel);
		panel.add(lPanel);
	}
}
