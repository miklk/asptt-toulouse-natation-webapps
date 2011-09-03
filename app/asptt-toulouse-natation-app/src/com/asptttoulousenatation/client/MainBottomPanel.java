package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;

public class MainBottomPanel extends Composite {

	private HorizontalPanel panel;
	
	public MainBottomPanel() {
		panel = new HorizontalPanel();
		panel.setStyleName(CSS.menuB());
		initWidget(panel);
		InlineLabel nlnlblContacts = new InlineLabel("Contacts");
		nlnlblContacts.setStyleName(CSS.menuBTitle());
		panel.add(nlnlblContacts);
//		bottomPanel.setWidgetLeftWidth(nlnlblContacts, 40.0, Unit.PCT, 20,
//				Unit.PCT);

		InlineLabel nlnlblSuiveznousrss = new InlineLabel("Suivez-nous (RSS)");
		nlnlblSuiveznousrss.setStyleName(CSS.menuBTitle());
		panel.add(nlnlblSuiveznousrss);
//		bottomPanel.setWidgetLeftWidth(nlnlblSuiveznousrss, 60, Unit.PCT, 20.0,
//				Unit.PCT);

		// InlineLabel nlnlblVisiteursDepuis = new
		// InlineLabel("150 visiteurs depuis mars 2010");
		// nlnlblVisiteursDepuis.setStyleName(CSS.menuBTitle());
		// bottomPanel.add(nlnlblVisiteursDepuis);
		// bottomPanel.setWidgetLeftWidth(nlnlblVisiteursDepuis, 80.0, Unit.PCT,
		// 1024.0, Unit.PX);
		// bottomPanel.setWidgetTopHeight(nlnlblVisiteursDepuis, 0.0, Unit.PX,
		// 28.0, Unit.PX);
	}
}
