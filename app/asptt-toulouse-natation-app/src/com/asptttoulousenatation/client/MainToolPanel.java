package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;
import static com.asptttoulousenatation.client.resources.ASPTT_ProtoResources.IMAGES;

import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent;
import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent.LoadContentAreaEnum;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
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
		Image lLogoArena = new Image(IMAGES.logoArenaPart());
		lLogoArena.setAltText("Boutique");
		lLogoArena.setTitle("Boutique");
		lLogoArena.getElement().getStyle().setCursor(Cursor.POINTER);
		lLogoArena.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent pEvent) {
				if (boutiqueData == null) {
					AreaUi lAreaUi = initResult.getArea("Boutique");
					if (lAreaUi != null) {
						MenuUi lMenu = lAreaUi.getMenu("Informations");
						eventBus.fireEvent(new LoadContentEvent(lMenu.getId(),
								LoadContentAreaEnum.TOOL));
					}
				} else {
					buildBoutiquePopup();
				}
			}
		});
		SimplePanel lImagePanel = new SimplePanel();
		lImagePanel.setWidget(lLogoArena);
		panel.add(new HeaderPanel("Boutique", lLogoArena));

		// Meteo
		// DockLayoutPanel dockLayoutPanel_Meteo = new DockLayoutPanel(Unit.EM);
		// lMeteoPanel.setStyleName(CSS.bloc());

		HTML lMeteo = new HTML(
				"<div id=\"cont_7b8dcd7d9f2587bff3518640f95fd205\"><h2 id=\"h_7b8dcd7d9f2587bff3518640f95fd205\"><a href=\"http://www.tameteo.com/\" title=\"M&eacute;t&eacute;o\">M&eacute;t&eacute;o</a></h2><a id=\"a_7b8dcd7d9f2587bff3518640f95fd205\" href=\"http://www.tameteo.com/meteo_Toulouse-Europe-France-Haute+Garonne-LFBO-1-26128.html\" target=\"_blank\" title=\"M&eacute;t&eacute;o Toulouse\" style=\"color:#656565;font-family:7;font-size:14px;\">M&eacute;t&eacute;o Toulouse</a><script type=\"text/javascript\" src=\"http://www.tameteo.com/wid_loader/7b8dcd7d9f2587bff3518640f95fd205\"></script></div>");
		panel.add(new HeaderPanel("Météo", lMeteo));
		// layoutPanel_1
		// .setWidgetLeftWidth(lMeteo, 82.0, Unit.PCT, 180.0, Unit.PX);
		// layoutPanel_1
		// .setWidgetTopHeight(lMeteo, 19.0, Unit.PCT, 110.0, Unit.PX);
		//
		// DockLayoutPanel dockLayoutPanel_2 = new DockLayoutPanel(Unit.EM);
		// dockLayoutPanel_2.setStyleName(CSS.bloc());
		// layoutPanel_1.add(dockLayoutPanel_2);
		// layoutPanel_1.setWidgetLeftWidth(dockLayoutPanel_2, 82.0, Unit.PCT,
		// 17.0, Unit.PCT);
		// layoutPanel_1.setWidgetTopHeight(dockLayoutPanel_2, 36.0, Unit.PCT,
		// 30.0, Unit.PCT);

		Label lblEvnements = new Label("Evènements");
		// lblEvnements.setStyleName(CSS.blocTitle());
		// dockLayoutPanel_2.addNorth(lblEvnements, 1.8);

		DatePicker lDatePicker = new DatePicker();
		DateTimeFormat lFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
		// Date[] lDates = new Date[] {lFormat.parse("10/03/2011"),
		// lFormat.parse("11/03/2011"), lFormat.parse("12/03/2011"),
		// lFormat.parse("13/03/2011"), lFormat.parse("18/03/2011"),
		// lFormat.parse("19/03/2011"), lFormat.parse("20/03/2011"),
		// lFormat.parse("15/04/2011"), lFormat.parse("16/04/2011"),
		// lFormat.parse("17/04/2011")};
		// lDatePicker.addStyleToDates(CSS.calendarEvt(),
		// Arrays.asList(lDates));
		// lDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
		//
		// public void onValueChange(ValueChangeEvent<Date> pEvent) {
		// Window.alert("Il y a un évènement ce jour là.");
		// }
		// });
		lDatePicker.setSize("100%", "100%");
		panel.add(new HeaderPanel("Evènement", lDatePicker));
		// dockLayoutPanel_2.add(lDatePicker);

		// Partenaires:
		// dockLayoutPanel_part.setStyleName(CSS.bloc());
		// layoutPanel_1.add(dockLayoutPanel_part);
		// layoutPanel_1.setWidgetLeftWidth(dockLayoutPanel_part, 82.0,
		// Unit.PCT,
		// 17.0, Unit.PCT);
		// layoutPanel_1.setWidgetTopHeight(dockLayoutPanel_part, 68.0,
		// Unit.PCT,
		// 30.0, Unit.PCT);

		// lTitlePart.setStyleName(CSS.blocTitle());
		AlternateBanner_Part lAlternateBanner_Part = new AlternateBanner_Part();
		panel.add(new HeaderPanel("Partenaires", lAlternateBanner_Part));
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
		Label lClose = new Label("X");
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