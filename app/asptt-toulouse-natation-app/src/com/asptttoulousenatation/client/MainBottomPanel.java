package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;
import static com.asptttoulousenatation.client.resources.ASPTT_ProtoResources.IMAGES;

import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent;
import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent.LoadContentAreaEnum;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;

public class MainBottomPanel extends Composite {

	private HorizontalPanel panel;
	
	private InitResult initResult;
	private PopupManager popupManager;
	private EventBus eventBus;
	
	private HTML contactData;
	
	public MainBottomPanel(InitResult pInitResult,
			EventBus pEventBus) {
		initResult = pInitResult;
		eventBus = pEventBus;
		panel = new HorizontalPanel();
		panel.setStyleName(CSS.menuB());
		initWidget(panel);
		InlineLabel nlnlblContacts = new InlineLabel("Contacts");
		nlnlblContacts.getElement().getStyle().setCursor(Cursor.POINTER);
		nlnlblContacts.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent pEvent) {
				if (contactData == null) {
					AreaUi lAreaUi = initResult.getArea("Contact");
					if (lAreaUi != null) {
						MenuUi lMenu = lAreaUi.getMenu("Contact");
						eventBus.fireEvent(new LoadContentEvent(lMenu,
								LoadContentAreaEnum.BOTTOM, lAreaUi.getTitle(), lMenu.getTitle()));
					}
				} else {
					buildContactPopup();
				}
				
			}
		});
		nlnlblContacts.setStyleName(CSS.menuBTitle());
		panel.add(nlnlblContacts);
		panel.setCellHorizontalAlignment(nlnlblContacts, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	public void setPopupManager(PopupManager pPopupManager) {
		popupManager = pPopupManager;
	}

	public void loadBottomContent(final byte[] pData) {
		contactData = new HTML(new String(pData));
		buildContactPopup();
	}
	
	private void buildContactPopup() {
		FlowPanel lFlowPanel = new FlowPanel();
		HorizontalPanel lHeader = new HorizontalPanel();
		lHeader.addStyleName(CSS.loginHeader());
		Label lTitle = new Label("Contact");
		lTitle.addStyleName(CSS.loginTitle());
		lHeader.add(lTitle);
		Image lClose = new Image(IMAGES.close());
		lClose.addStyleName(CSS.loginClose());
		lClose.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				popupManager.hide();
			}
		});
		lHeader.add(lClose);
		lHeader.setCellHorizontalAlignment(lTitle,
				HasHorizontalAlignment.ALIGN_LEFT);
		lHeader.setCellHorizontalAlignment(lClose,
				HasHorizontalAlignment.ALIGN_RIGHT);
		lClose.getElement().getStyle().setMarginLeft(50, Unit.PX);
		lFlowPanel.add(lHeader);
		FlowPanel lIdPanel = new FlowPanel();
		lIdPanel.addStyleName(CSS.loginContent());

		lIdPanel.add(contactData);
		lFlowPanel.add(lIdPanel);
		popupManager.createPopup(true, true, lFlowPanel);
		popupManager.center();

	}
}
