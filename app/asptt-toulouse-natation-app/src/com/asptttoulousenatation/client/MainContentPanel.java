package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.List;

import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent;
import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent.LoadContentAreaEnum;
import com.asptttoulousenatation.client.util.Breadcrumb;
import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.EventBus;

public class MainContentPanel extends Composite {

	private VerticalPanel panel;
	private SimplePanel content;
	private FlowPanel actuPanel;
	private Breadcrumb breadcrumb;

	private InitResult initResult;
	private PopupManager popupManager;
	private MenuUi selectedMenu;
	private EventBus eventBus;
	private Panel subMenu;
	
	private Button moreActuButton;
	private long moreActuEnd;

	public MainContentPanel(InitResult pInitResult, EventBus pEventBus) {
		initResult = pInitResult;
		eventBus = pEventBus;
		panel = new VerticalPanel();
		initWidget(panel);

		panel.setStyleName(CSS.mainBody());

		content = new ScrollPanel();
		content.addStyleName(CSS.mainContent());

		VerticalPanel lTitlePanel = new VerticalPanel();
		lTitlePanel.setStyleName(CSS.titrePanel());
		Label lblAspttGrandToulouse = new Label("Accueil");
		lblAspttGrandToulouse.setStyleName(CSS.accueilTitre());
		lblAspttGrandToulouse.getElement().getStyle().setCursor(Cursor.POINTER);
		lblAspttGrandToulouse.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				subMenu.clear();
				content.setWidget(actuPanel);
				breadcrumb.update("", "");
			}
		});
		lTitlePanel.add(lblAspttGrandToulouse);
		
		breadcrumb = new Breadcrumb();
		breadcrumb.setStyleName(CSS.tetiere());
		breadcrumb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				subMenu.clear();
				content.setWidget(actuPanel);
				breadcrumb.update("", "");
			}
		});
		panel.add(breadcrumb);
		panel.setCellHeight(breadcrumb, "10px");
		
		Label lInfoSupp = new Label("Ecole de natation - Il reste des places !");
		lInfoSupp.setStyleName(CSS.actuSpecialTitle());
		lInfoSupp.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent pEvent) {
				PopupPanel lPopupPanel = new PopupPanel(true);
				SimplePanel lPanel = new SimplePanel();
				lPanel.setStyleName(CSS.actuSpecial());
				lPanel.setWidget(new HTML("<p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\">Suite &agrave; un &eacute;tat des lieux de nos groupes de l&#39;Ecole de Natation, des Loisirs et de l&#39;Aquagym, veuillez trouver ci-joint d&eacute;taill&eacute;es, les s&eacute;ances o&ugrave; vous pourrez venir pratiquer votre discipline favorite dans les meilleures conditions :</span></span></p><p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\"><strong>1)</strong> <u><strong>Tritons (d&eacute;butants de 2009 et +)</strong></u> : Lundi de 19h &agrave; 19h45 sur Toulouse-Lautrec, Mercredi de 14h30 &agrave; 15h et de 16h &agrave; 16h45 sur Raymond Naves, &nbsp;Jeudi de 18h45 &agrave; 19h30 sur L&eacute;o Lagrange.</span></span></p><p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\"><strong>2) <u>Dauphins (d&eacute;brouill&eacute;s de 2008 et )&nbsp;</u></strong>: Mercredi de 19h45 &agrave; 20h30 sur Toulouse-Lautrec, Mercredi de 16h &agrave; 16h45 sur Raymond Naves, Samedi de 10h30 &agrave; 11h15 sur Alex Jany.</span></span></p><p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\"><strong>3) <u>Licornes </u>: </strong>Lundi de 19h45 &agrave; 20h30 sur Toulouse-Lautrec, Mercredi de 15h15 &agrave; 16h sur Raymond Naves, Jeudi de 18h45 &agrave; 19h30 sur L&eacute;o Lagrange, Vendredi de 18h &agrave; 19h ou de 19h &agrave; 20h sur L&eacute;o Lagrange, Vendredi de 19h55 &agrave; 20h45 sur Alex Jany, Samedi de 11h15 &agrave; 12h sur Alex Jany.</span></span></p><p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\"><strong>4) <u>Avenirs (confirm&eacute;s n&eacute;s en 2005, 2006) </u></strong>: Mercredi de 16h &agrave; 16h45 sur Raymond Naves, Mercredi de 19h &agrave; 19h45 sur Toulouse-Lautrec, Vendredi de 18h &agrave; 19h sur L&eacute;o Lagrange, Vendredi de 19h &agrave; 19h50 sur Alex Jany, Samedi de 09h &agrave; 09h45 sur Alex Jany.</span></span></p><p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\"><strong>5) <u>Marsouins (n&eacute;s en 2003 et 2004)</u></strong><u> </u>: Lundi &nbsp;et Mercredi de 19h &agrave; 19h45 sur Toulouse-Lautrec, Mercredi de 15h15 &agrave; 16h sur Raymond Naves, Jeudi de 18h45 &agrave; 19h30 sur L&eacute;o Lagrange, Vendredi de 19h &agrave; 20h sur L&eacute;o Lagrange, Samedi de 09h &agrave; 09h45 et de 11h15 &agrave; 12h sur Alex Jany.</span></span></p><p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\"><strong>6) <u>Cachalots (n&eacute;s en 2000, 2001 et 2002) </u></strong>: Mercredi de 14h30 &agrave; 15h15 sur Raymond Naves et Mercredi de 19h45 &agrave; 20h30 sur Toulouse-Lautrec.</span></span></p><p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\"><strong>7) <u>Loisirs Adultes (perfectionnement 1992 et +) </u></strong>: Mercredi de 19h &agrave; 19h45 et de 19h45 &agrave; 20h30 sur Toulouse-Lautrec, Vendredi de 19h55 &agrave; 20h45 sur Alex Jany.</span></span></p><p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\"><strong>8) <u>Adultes D&eacute;butants&nbsp;(apprentissage) </u></strong>: Vendredi de 19h55 &agrave; 20h45 sur Alex Jany.</span></span></p><p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\"><strong>9) <u>Aquagym </u></strong>: Mardi de 19h55 &agrave; 20h45 sur Alex Jany, Jeudi de 18h &agrave; 18h45 ou de 18h45 &agrave; 19h30 sur L&eacute;o Lagrange, Vendredi de 18h &agrave; 19h sur L&eacute;o Lagrange, Vendredi de 19h55 &agrave; 20h45 sur Alex Jany.</span></span></p><p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\">A noter qu&#39;en ce d&eacute;but de second trimestre de pratique, un prorata de la cotisation vous sera propos&eacute;, ce qui passe l&#39;inscription sur l&#39;Ecole de Nage &agrave; 193 euros (&agrave; la place de 250) et sur l&#39;Aquagym &agrave; 213 (au lieu de 280).</span></span></p><p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\">Si vous souhaitez passer &agrave; l&#39;acte, suivez le lien suivant (<a href=\"http://www.asptt-toulouse-natation.com/v2/inscription.html\">http://www.asptt-toulouse-natation.com/v2/inscription.html</a>).</span></span></p><p><span style=\"\"><span style=\"font-family:comic sans ms,cursive;\">N&#39;h&eacute;sitez pas &agrave; nous rejoindre dans les plus brefs d&eacute;lais !!!</span></span></p>"));
				lPopupPanel.add(lPanel);
				lPopupPanel.setSize("500px", "100%");
				lPopupPanel.center();
			}
		});
		panel.add(lInfoSupp);
		panel.setCellHeight(lInfoSupp, "10px");
		

		subMenu = new HorizontalPanel();
		panel.add(subMenu);
		panel.setCellHeight(subMenu, "20px");
		
		// TODO Actu
		panel.add(content);
		actuPanel = new FlowPanel();
		actuPanel.setStyleName(CSS.actuBloc());
		content.setWidget(actuPanel);
		buildActuPanel();
	}

	private void buildActuPanel() {
		moreActuEnd = initResult.getActuEnd();
		Label lblALaUne = new Label("A la une");
		lblALaUne.setStyleName(CSS.blocTitle());
		actuPanel.add(lblALaUne);
		DateTimeFormat lDateTimeFormat = DateTimeFormat
				.getFormat("dd MMMM yyyy");
		for (ActuUi lActuUi : initResult.getActu()) {
			DisclosurePanel lActuDetail = new DisclosurePanel();
			lActuDetail.setOpen(true);
			HorizontalPanel headerPanel = new HorizontalPanel();
			Label open = new Label("+");
			open.getElement().getStyle().setMarginRight(5, Unit.PX);
			headerPanel.add(open);
			headerPanel.add(new HTML(
					lActuUi.getSummary()));
			lActuDetail.setHeader(headerPanel);
			VerticalPanel lContentPanel = new VerticalPanel();
			lContentPanel.add(new HTML(lActuUi.getContent()));
			lContentPanel.add(getActuDocumentPanel(lActuUi.getDocumentSet()));
			lActuDetail.add(lContentPanel);
			lActuDetail.getContent().getElement().getStyle()
					.clearBackgroundColor();
			HeaderPanel lHeaderPanel = new HeaderPanel(
					lDateTimeFormat.format(lActuUi.getCreationDate()) + " - "
							+ lActuUi.getTitle(), lActuDetail);
			lHeaderPanel.setHeaderStyle(CSS.actuBlocHeader());
			lHeaderPanel.setContentStyle(CSS.actuBlocContent());	
			lHeaderPanel.isOdd();
			actuPanel.add(lHeaderPanel);
		}
		moreActuButton = new Button("Plus anciennes");
		moreActuButton.setStyleName(CSS.moreActuButton());
		actuPanel.add(moreActuButton);
	}
	
	public void updateBreadcrumb(final String pAreaName, final String pMenuName) {
		breadcrumb.update(pAreaName, pMenuName);
	}
	
	public void updateBreadcrumb(final String pMenuName) {
		breadcrumb.update(pMenuName);
	}
	
	private void buildSubMenu() {
		subMenu.clear();
		subMenu.removeStyleName(CSS.menuSub());
		if(selectedMenu != null && CollectionUtils.isNotEmpty(selectedMenu.getSubMenus())) {
			subMenu.addStyleName(CSS.menuSub());
			for(final MenuUi lSubMenu: selectedMenu.getSubMenus()) {
				Label lLabel = new Label(lSubMenu.getTitle());
				lLabel.addStyleName(CSS.menuSubLabel());
				lLabel.addClickHandler(new ClickHandler() {
					
					public void onClick(ClickEvent pEvent) {
						eventBus.fireEvent(new LoadContentEvent(lSubMenu, LoadContentAreaEnum.SUB_CONTENT, null,
								lSubMenu.getTitle()));
					}
				});
				subMenu.add(lLabel);
			}
			panel.setCellHeight(subMenu, "20px");	
		}
	}

	public void loadContent(final byte[] pData, List<DocumentUi> pDocuments) {
		if (pData != null) {
			VerticalPanel lPanel = new VerticalPanel();
			String lData = new String(pData);
			lPanel.add(new HTML(lData));

			// Document
			if (CollectionUtils.isNotEmpty(pDocuments)) {
				lPanel.add(getDocumentPanel(pDocuments));
			}
			content.setWidget(lPanel);
			popupManager.hide();
		}
		buildSubMenu();
	}
	
	public void loadContent(final Panel pPanel) {
			content.setWidget(pPanel);
			popupManager.hide();
		buildSubMenu();
	}

	private Panel getDocumentPanel(List<DocumentUi> pDocuments) {
		Grid lPanel = new Grid(2, 1);
		lPanel.setStyleName(CSS.areaDocumentList());
		// Header
		Label lTitle = new Label("Documents à télécharger");
		lTitle.setStyleName(CSS.areaDocumentListTitle());
		lPanel.setWidget(0, 0, lTitle);

		// Documents
		VerticalPanel lDocumentPanel = new VerticalPanel();
		for (DocumentUi lDocument : pDocuments) {
			String text = lDocument.getTitle();
			if(lDocument.getSummary() != null && !lDocument.getSummary().isEmpty()) {
				text+= " - " + lDocument.getSummary();
			}
			Anchor lAnchor = new Anchor(text);
			lAnchor.setTitle(lDocument.getSummary());
			lAnchor.setHref(GWT.getHostPageBaseURL()
					+ "downloadDocument?documentId=" + lDocument.getId()
					+ "&fileId=" + lDocument.getData());
			lAnchor.addStyleName(CSS.areaDocumentItem());
			lDocumentPanel.add(lAnchor);
		}
		lPanel.setWidget(1, 0, lDocumentPanel);
		return lPanel;
	}
	
	private Panel getActuDocumentPanel(List<DocumentUi> pDocuments) {
		// Documents
		VerticalPanel lDocumentPanel = new VerticalPanel();
		for (DocumentUi lDocument : pDocuments) {
			String text = lDocument.getTitle();
			if(lDocument.getSummary() != null && !lDocument.getSummary().isEmpty()) {
				text+= " - " + lDocument.getSummary();
			}
			Anchor lAnchor = new Anchor(text);
			lAnchor.setTitle(lDocument.getSummary());
			lAnchor.setHref(GWT.getHostPageBaseURL()
					+ "downloadDocument?documentId=" + lDocument.getId()
					+ "&fileId=" + lDocument.getData());
			lAnchor.addStyleName(CSS.areaDocumentItem());
			lDocumentPanel.add(lAnchor);
		}
		return lDocumentPanel;
	}

	public void setPopupManager(PopupManager pPopupManager) {
		popupManager = pPopupManager;
	}
	
	public void setSelectedMenu(MenuUi pMenu) {
		selectedMenu = pMenu;
	}
	
	public void loadSubContent(final byte[] pData, List<DocumentUi> pDocuments, String pSubMenuName) {
		breadcrumb.update(pSubMenuName);
		loadContent(pData, pDocuments);
	}
	
	public HasClickHandlers getMoreActuButton() {
		return moreActuButton;
	}
	
	public long getMoreActuEnd() {
		return moreActuEnd;
	}
	
	public void setMoreActu(List<ActuUi> pActu, long end) {
		moreActuEnd = end;
		actuPanel.remove(moreActuButton);
		DateTimeFormat lDateTimeFormat = DateTimeFormat
				.getFormat("dd MMMM yyyy");
		for (ActuUi lActuUi : pActu) {
			DisclosurePanel lActuDetail = new DisclosurePanel();
			HorizontalPanel headerPanel = new HorizontalPanel();
			Label open = new Label("+");
			open.getElement().getStyle().setMarginRight(5, Unit.PX);
			headerPanel.add(open);
			headerPanel.add(new HTML(
					lActuUi.getSummary()));
			lActuDetail.setHeader(headerPanel);
			VerticalPanel lContentPanel = new VerticalPanel();
			lContentPanel.add(new HTML(lActuUi.getContent()));
			lContentPanel.add(getActuDocumentPanel(lActuUi.getDocumentSet()));
			lActuDetail.add(lContentPanel);
			lActuDetail.getContent().getElement().getStyle()
					.clearBackgroundColor();
			HeaderPanel lHeaderPanel = new HeaderPanel(
					lDateTimeFormat.format(lActuUi.getCreationDate()) + " - "
							+ lActuUi.getTitle(), lActuDetail);
			lHeaderPanel.setHeaderStyle(CSS.actuBlocHeader());
			lHeaderPanel.setContentStyle(CSS.actuBlocContent());	
			lHeaderPanel.isOdd();
			actuPanel.add(lHeaderPanel);
		}
		actuPanel.add(moreActuButton);
	}
}