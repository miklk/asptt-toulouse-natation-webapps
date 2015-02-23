package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.List;

import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent;
import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent.LoadContentAreaEnum;
import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.EventBus;

public class MainContentPanel extends Composite {

	private VerticalPanel panel;
	private SimplePanel content;
	private FlowPanel actuPanel;

	private PopupManager popupManager;
	private MenuUi selectedMenu;
	private EventBus eventBus;
	private Panel subMenu;
	
	private Button moreActuButton;
	private long moreActuEnd;

	public MainContentPanel(EventBus pEventBus) {
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
			}
		});
		lTitlePanel.add(lblAspttGrandToulouse);
		
		subMenu = new HorizontalPanel();
		panel.add(subMenu);
		panel.setCellHeight(subMenu, "20px");
		
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