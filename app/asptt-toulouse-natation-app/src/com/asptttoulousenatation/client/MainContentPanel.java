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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
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

	public MainContentPanel(InitResult pInitResult, EventBus pEventBus) {
		initResult = pInitResult;
		eventBus = pEventBus;
		panel = new VerticalPanel();
		initWidget(panel);

		panel.setStyleName(CSS.mainBody());

		content = new ScrollPanel();
		content.addStyleName(CSS.mainContent());

		Label lblAspttGrandToulouse = new Label("ASPTT Grand Toulouse Natation");
		lblAspttGrandToulouse.setStyleName(CSS.title());
		panel.add(lblAspttGrandToulouse);
		panel.setCellHeight(lblAspttGrandToulouse, "20px");
		
		HTML oldAnchor = new HTML("<a href=\"http://asptt-toulouse-natation.com\">Ancienne version du site</a>");
		panel.add(oldAnchor);
		panel.setCellHeight(oldAnchor, "20px");

		breadcrumb = new Breadcrumb();
		breadcrumb.update("Accueil", "");
		breadcrumb.setStyleName(CSS.tetiere());
		breadcrumb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				subMenu.clear();
				content.setWidget(actuPanel);
				breadcrumb.update("Accueil", "");
			}
		});
		panel.add(breadcrumb);
		panel.setCellHeight(breadcrumb, "10px");

		subMenu = new HorizontalPanel();
		panel.add(subMenu);
		panel.setCellHeight(subMenu, "0px");
		
		// TODO Actu
		panel.add(content);
		actuPanel = new FlowPanel();
		actuPanel.setStyleName(CSS.bloc());
		content.setWidget(actuPanel);
		buildActuPanel();
	}

	private void buildActuPanel() {
		Label lblALaUne = new Label("A la une");
		lblALaUne.setStyleName(CSS.blocTitle());
		actuPanel.add(lblALaUne);
		DateTimeFormat lDateTimeFormat = DateTimeFormat
				.getFormat("dd MMMM yyyy - HH:mm:ss");
		for (ActuUi lActuUi : initResult.getActu()) {
			DisclosurePanel lActuDetail = new DisclosurePanel(
					lActuUi.getSummary());
			VerticalPanel lContentPanel = new VerticalPanel();
			lContentPanel.add(new HTML(lActuUi.getContent()));
			lContentPanel.add(getActuDocumentPanel(lActuUi.getDocumentSet()));
			lActuDetail.add(lContentPanel);
			lActuDetail.getContent().getElement().getStyle()
					.clearBackgroundColor();
			HeaderPanel lHeaderPanel = new HeaderPanel(
					lDateTimeFormat.format(lActuUi.getCreationDate()) + " - "
							+ lActuUi.getTitle(), lActuDetail);
			lHeaderPanel.isOdd();
			actuPanel.add(lHeaderPanel);
		}
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
}