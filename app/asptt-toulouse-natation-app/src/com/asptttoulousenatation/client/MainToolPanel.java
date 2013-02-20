package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;
import static com.asptttoulousenatation.client.resources.ASPTT_ProtoResources.IMAGES;

import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent;
import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent.LoadContentAreaEnum;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.event.UiEvent;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.web.bindery.event.shared.EventBus;

public class MainToolPanel extends Composite {

	private VerticalPanel panel;

	private InitResult initResult;
	private PopupManager popupManager;

	private EventBus eventBus;
	private HTML boutiqueData;

	public MainToolPanel(InitResult pInitResult, UserUi pUser,
			EventBus pEventBus) {
		initResult = pInitResult;
		eventBus = pEventBus;
		panel = new VerticalPanel();
		initWidget(panel);

		
		// Arena
		Timer boutiqueTimer = new Timer() {
			
			@Override
			public void run() {
		Image lLogoArena = new Image(IMAGES.logoArenaBoutique());
		lLogoArena.setAltText("Boutique");
		lLogoArena.setTitle("Boutique");
		lLogoArena.getElement().getStyle().setCursor(Cursor.POINTER);
		lLogoArena.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent pEvent) {
					AreaUi lAreaUi = initResult.getArea("Boutique");
					if (lAreaUi != null) {
						MenuUi lMenu = lAreaUi.getMenu("Boutique");
						eventBus.fireEvent(new LoadContentEvent(lMenu,
								LoadContentAreaEnum.CONTENT, lAreaUi.getTitle(),
								lMenu.getTitle()));
					}
			}
		});
		SimplePanel lImagePanel = new SimplePanel();
		lImagePanel.setWidget(lLogoArena);
		HeaderPanel lBoutiquePanel = new HeaderPanel("Boutique", lLogoArena);
		lBoutiquePanel.getHeader().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				AreaUi boutiqueArea = initResult.getArea("Boutique");
				MenuUi boutiqueMenu = boutiqueArea.getMenu("Boutique");
				eventBus.fireEvent(new LoadContentEvent(boutiqueMenu, LoadContentAreaEnum.CONTENT, boutiqueArea.getTitle(), boutiqueMenu.getTitle()));
			}
		});
		lBoutiquePanel.addStyleName(CSS.bloc());
		lBoutiquePanel.setHeaderStyle(CSS.blocTitle());
		panel.add(lBoutiquePanel);
			}
		};
		boutiqueTimer.schedule(3000);

		Timer timer = new Timer() {

			@Override
			public void run() {

				final DatePicker lDatePicker = new DatePicker();
				// Get event dates
				lDatePicker.addStyleToDates(CSS.calendarEvt(), initResult
						.getEvents().keySet());

				lDatePicker
						.addValueChangeHandler(new ValueChangeHandler<Date>() {
							public void onValueChange(
									ValueChangeEvent<Date> pEvent) {
								Date lSearchDate = new Date(Date.UTC(pEvent
										.getValue().getYear(), pEvent
										.getValue().getMonth(), pEvent
										.getValue().getDate(), 0, 0, 0));
								List<UiEvent> lUiEvents = initResult
										.getEvents().get(lSearchDate);
								if (lUiEvents != null && !lUiEvents.isEmpty()) {
									StringBuilder lMsg = new StringBuilder();
									for (UiEvent lUiEvent : lUiEvents) {
										lMsg.append(lUiEvent.getEventTitle())
												.append("<br />");
									}
									popupManager.createPopup(true, true,
											new HTML(lMsg.toString()));
									popupManager.showRelativeTo(lDatePicker);
								}
							}
						});
				lDatePicker.setSize("100%", "100%");
				HeaderPanel lEventPanel = new HeaderPanel("Evènement",
						lDatePicker);
				lEventPanel.getHeader().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						AreaUi eventArea = initResult.getArea("Compétitions");
						MenuUi eventMenu = eventArea.getMenu("Calendrier");
						eventBus.fireEvent(new LoadContentEvent(eventMenu, LoadContentAreaEnum.CONTENT, eventArea.getTitle(), eventMenu.getTitle()));
					}
				});
				lEventPanel.setHeaderStyle(CSS.blocTitle());
				lEventPanel.addStyleName(CSS.bloc());
				panel.add(lEventPanel);

				AlternateBanner_Part lAlternateBanner_Part = new AlternateBanner_Part();
				HeaderPanel lPartnerPanel = new HeaderPanel("Partenaires",
						lAlternateBanner_Part);
				lPartnerPanel.getHeader().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						AreaUi partnerArea = initResult.getArea("Club des partenaires");
						MenuUi partnerMenu = partnerArea.getMenu("Partenaires");
						eventBus.fireEvent(new LoadContentEvent(partnerMenu, LoadContentAreaEnum.CONTENT, partnerArea.getTitle(), partnerMenu.getTitle()));
					}
				});
				lPartnerPanel.setHeaderStyle(CSS.blocTitle());
				lPartnerPanel.addStyleName(CSS.bloc());
				panel.add(lPartnerPanel);
			}
		};
		timer.schedule(2000);
	}

	public void setPopupManager(PopupManager pPopupManager) {
		popupManager = pPopupManager;
	}

	public void loadToolContent(final byte[] pData) {
		boutiqueData = new HTML(new String(pData));
		buildBoutiquePopup();
	}

	private void buildBoutiquePopup() {
		FlowPanel lFlowPanel = new FlowPanel();
		HorizontalPanel lHeader = new HorizontalPanel();
		lHeader.addStyleName(CSS.loginHeader());
		Label lTitle = new Label("Boutique");
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

		lIdPanel.add(boutiqueData);
		lFlowPanel.add(lIdPanel);
		popupManager.createPopup(true, true, lFlowPanel);
		popupManager.center();

	}
}