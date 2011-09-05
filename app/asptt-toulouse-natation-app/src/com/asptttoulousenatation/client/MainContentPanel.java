package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainContentPanel extends Composite {

	private VerticalPanel panel;
	private SimplePanel content;
	private FlowPanel actuPanel;
	
	private InitResult initResult;
	private PopupManager popupManager;
private UserUi user;
	
	private EventBus eventBus;
	
	public MainContentPanel(InitResult pInitResult, UserUi pUser, EventBus pEventBus) {
		initResult = pInitResult;
		eventBus = pEventBus;
		user = pUser;
		panel = new VerticalPanel();
		initWidget(panel);
		
		panel.setStyleName(CSS.mainContent());
		
		content = new SimplePanel();
		
		
		Label lblAspttGrandToulouse = new Label("ASPTT Grand Toulouse Natation");
		lblAspttGrandToulouse.setStyleName(CSS.title());
		panel.add(lblAspttGrandToulouse);
		panel.setCellHeight(lblAspttGrandToulouse, "20px");
//		layoutPanel_1.setWidgetLeftWidth(lblAspttGrandToulouse, 18.0, Unit.PX,
//				60.0, Unit.PCT);
//		layoutPanel_1.setWidgetTopHeight(lblAspttGrandToulouse, 0.0, Unit.PCT,
//				10.0, Unit.PCT);

		Label lblAccueil = new Label("ASPTT Grand Toulouse Natation -> Accueil");
		lblAccueil.setStyleName(CSS.tetiere());
		lblAccueil.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				content.setWidget(actuPanel);
			}
		});
		panel.add(lblAccueil);
		panel.setCellHeight(lblAccueil, "10px");
		
		// TODO Actu
		panel.add(content);
		actuPanel = new FlowPanel();
		actuPanel.setStyleName(CSS.bloc());
		content.setWidget(actuPanel);
		buildActuPanel();
//		layoutPanel_1.setWidgetLeftWidth(lblAccueil, 10.0, Unit.PX, 510.0,
//				Unit.PX);
//		layoutPanel_1.setWidgetTopHeight(lblAccueil, 5.0, Unit.PCT, 18.0,
//				Unit.PX);
	}
	private void buildActuPanel() {
		Label lblALaUne = new Label("A la une");
		lblALaUne.setStyleName(CSS.blocTitle());
		actuPanel.add(lblALaUne);
		DateTimeFormat lDateTimeFormat = DateTimeFormat.getFormat("dd MMMM yyyy - HH:mm:ss");
		for (ActuUi lActuUi : initResult.getActu()) {
			DisclosurePanel lActuDetail = new DisclosurePanel(lActuUi.getSummary());
			lActuDetail.add(new HTML(lActuUi.getContent()));
			lActuDetail.getContent().getElement().getStyle().clearBackgroundColor();
			HeaderPanel lHeaderPanel = new HeaderPanel(
					lDateTimeFormat.format(lActuUi.getCreationDate()) + " - "
							+ lActuUi.getTitle(), lActuDetail);
			lHeaderPanel.isOdd();
			actuPanel.add(lHeaderPanel);
		}
	}

	
	public void loadContent(final byte[] pData) {
		if(pData != null) {
				String lData = new String(pData);
				content.setWidget(new HTML(lData));
				popupManager.hide();
		}
	}
	
	public void setPopupManager(PopupManager pPopupManager) {
		popupManager = pPopupManager;
	}
}