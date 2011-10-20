package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.List;

import com.asptttoulousenatation.client.util.Breadcrumb;
import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainContentPanel extends Composite {

	private VerticalPanel panel;
	private SimplePanel content;
	private FlowPanel actuPanel;
	private Breadcrumb breadcrumb;

	private InitResult initResult;
	private PopupManager popupManager;

	public MainContentPanel(InitResult pInitResult) {
		initResult = pInitResult;
		panel = new VerticalPanel();
		initWidget(panel);

		panel.setStyleName(CSS.mainBody());

		content = new ScrollPanel();
		content.addStyleName(CSS.mainContent());

		Label lblAspttGrandToulouse = new Label("ASPTT Grand Toulouse Natation");
		lblAspttGrandToulouse.setStyleName(CSS.title());
		panel.add(lblAspttGrandToulouse);
		panel.setCellHeight(lblAspttGrandToulouse, "20px");

		breadcrumb = new Breadcrumb();
		breadcrumb.update("Accueil", "");
		breadcrumb.setStyleName(CSS.tetiere());
		breadcrumb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				content.setWidget(actuPanel);
				breadcrumb.update("Accueil", "");
			}
		});
		panel.add(breadcrumb);
		panel.setCellHeight(breadcrumb, "10px");

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
			lActuDetail.add(new HTML(lActuUi.getContent()));
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
	}

	private Panel getDocumentPanel(List<DocumentUi> pDocuments) {
		Grid lPanel = new Grid(2, 1);
		lPanel.setStyleName(CSS.areaDocumentList());
		// Header
		Label lTitle = new Label("Documents à télécharger");
		lTitle.setStyleName(CSS.areaDocumentListTitle());
		lPanel.setWidget(0, 0, lTitle);

		// Documents
		FlowPanel lDocumentPanel = new FlowPanel();
		for (DocumentUi lDocument : pDocuments) {
			Anchor lAnchor = new Anchor(lDocument.getTitle());
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

	public void setPopupManager(PopupManager pPopupManager) {
		popupManager = pPopupManager;
	}
}