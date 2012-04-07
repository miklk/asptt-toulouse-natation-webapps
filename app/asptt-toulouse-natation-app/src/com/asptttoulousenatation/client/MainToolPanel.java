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
import com.google.gwt.event.shared.EventBus;
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

public class MainToolPanel extends Composite {

	private VerticalPanel panel;

	private InitResult initResult;
	private PopupManager popupManager;
	private UserUi user;

	private EventBus eventBus;
	private HTML boutiqueData;

	public MainToolPanel(InitResult pInitResult, UserUi pUser,
			EventBus pEventBus) {
		initResult = pInitResult;
		eventBus = pEventBus;
		user = pUser;
		panel = new VerticalPanel();
		initWidget(panel);

		// Arena
		Image lLogoArena = new Image(IMAGES.logoArenaBoutique());
		lLogoArena.setAltText("Boutique");
		lLogoArena.setTitle("Boutique");
		lLogoArena.getElement().getStyle().setCursor(Cursor.POINTER);
		lLogoArena.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent pEvent) {
				if (boutiqueData == null) {
					AreaUi lAreaUi = initResult.getArea("Boutique");
					if (lAreaUi != null) {
						MenuUi lMenu = lAreaUi.getMenu("Informations");
						eventBus.fireEvent(new LoadContentEvent(lMenu,
								LoadContentAreaEnum.TOOL, lAreaUi.getTitle(), lMenu.getTitle()));
					}
				} else {
					buildBoutiquePopup();
				}
			}
		});
		SimplePanel lImagePanel = new SimplePanel();
		lImagePanel.setWidget(lLogoArena);
		HeaderPanel lBoutiquePanel = new HeaderPanel("Boutique", lLogoArena);
		lBoutiquePanel.addStyleName(CSS.bloc());
		lBoutiquePanel.setHeaderStyle(CSS.blocTitle());
		panel.add(lBoutiquePanel);

//		// Meteo
//		HTML lMeteo = new HTML(
//				"<div id=\"widget_98a7717ff38d2f8635c0a2316bfabad6\"><a href=\"http://www.my-meteo.fr/previsions+meteo+france/toulouse.html\" title=\"M&eacute;t&eacute;o Toulouse\">M&eacute;t&eacute;o Toulouse</a><script type=\"text/javascript\" src=\"http://www.my-meteo.fr/meteo+webmaster/widget/js.php?ville=263&amp;format=petit-vertical&amp;nb_jours=2&amp;icones&amp;horaires&amp;vent&amp;c1=414141&amp;c2=21a2f3&amp;c3=d4d4d4&amp;c4=FFF&amp;id=98a7717ff38d2f8635c0a2316bfabad6\"></script></div>");
////				"<div id=\"cont_7b8dcd7d9f2587bff3518640f95fd205\"><h2 id=\"h_7b8dcd7d9f2587bff3518640f95fd205\"><a href=\"http://www.tameteo.com/\" title=\"M&eacute;t&eacute;o\">M&eacute;t&eacute;o</a></h2><a id=\"a_7b8dcd7d9f2587bff3518640f95fd205\" href=\"http://www.tameteo.com/meteo_Toulouse-Europe-France-Haute+Garonne-LFBO-1-26128.html\" target=\"_blank\" title=\"M&eacute;t&eacute;o Toulouse\" style=\"color:#656565;font-family:7;font-size:14px;\">M&eacute;t&eacute;o Toulouse</a><script type=\"text/javascript\" src=\"http://www.tameteo.com/wid_loader/7b8dcd7d9f2587bff3518640f95fd205\"></script></div>");
//		HeaderPanel lMeteoPanel = new HeaderPanel("Météo", lMeteo);
//		lMeteoPanel.addStyleName(CSS.bloc());
//		lMeteoPanel.setHeaderStyle(CSS.blocTitle());
//		panel.add(lMeteoPanel);

		final DatePicker lDatePicker = new DatePicker();
		// Get event dates
		lDatePicker.addStyleToDates(CSS.calendarEvt(), initResult.getEvents()
				.keySet());

		lDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> pEvent) {
				Date lSearchDate = new Date(Date.UTC(pEvent.getValue().getYear(), pEvent.getValue().getMonth(), pEvent.getValue().getDate(), 0, 0, 0));
				List<UiEvent> lUiEvents = initResult.getEvents()
						.get(lSearchDate);
				if (lUiEvents != null && !lUiEvents.isEmpty()) {
					StringBuilder lMsg = new StringBuilder();
					for(UiEvent lUiEvent: lUiEvents) {
						lMsg.append(lUiEvent.getEventTitle()).append("<br />");
					}
					popupManager.createPopup(true, true, new HTML(lMsg.toString()));
					popupManager.showRelativeTo(lDatePicker);
				}
			}
		});
		lDatePicker.setSize("100%", "100%");
		HeaderPanel lEventPanel = new HeaderPanel("Evènement", lDatePicker);
		lEventPanel.setHeaderStyle(CSS.blocTitle());
		lEventPanel.addStyleName(CSS.bloc());
		panel.add(lEventPanel);

		AlternateBanner_Part lAlternateBanner_Part = new AlternateBanner_Part();
		HeaderPanel lPartnerPanel = new HeaderPanel("Partenaires",
				lAlternateBanner_Part);
		lPartnerPanel.setHeaderStyle(CSS.blocTitle());
		lPartnerPanel.addStyleName(CSS.bloc());
		panel.add(lPartnerPanel);
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