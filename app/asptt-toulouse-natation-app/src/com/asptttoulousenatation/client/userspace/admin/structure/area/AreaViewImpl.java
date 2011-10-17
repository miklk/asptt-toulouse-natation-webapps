package com.asptttoulousenatation.client.userspace.admin.structure.area;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.List;

import com.asptttoulousenatation.client.userspace.admin.structure.menu.ui.MenuCell;
import com.asptttoulousenatation.client.userspace.admin.ui.DocumentCell;
import com.asptttoulousenatation.client.userspace.admin.util.CellListStyle;
import com.asptttoulousenatation.client.userspace.document.DocumentWidget;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentUI;
import com.axeiya.gwtckeditor.client.CKConfig;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.axeiya.gwtckeditor.client.Toolbar;
import com.axeiya.gwtckeditor.client.ToolbarLine;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class AreaViewImpl extends Composite implements AreaView {

	private List<MenuUi> data;
	private HorizontalPanel panel;

	private CellList<MenuUi> cellList;
	private SingleSelectionModel<MenuUi> selectionModel;
	private SimplePanel editionPanel;

	private TextBox menuTitleInput;
	private TextBox summaryInput;
	private CKEditor contentInput;
	private Button updateButton;
	
	//Document edition
	private HorizontalPanel documentPanel;
	private CellList<DocumentUi> documentCellList;
	private SingleSelectionModel<DocumentUi> documentSelectionModel;
	private SimplePanel documentEditionPanel;
	private TextBox documentTitleInput;
	private TextBox documentSummaryInput;
	private Button documentUpdateButton;
	private Button documentDeleteButton;

	public AreaViewImpl(List<MenuUi> pData) {
		data = pData;
		panel = new HorizontalPanel();
		initWidget(panel);

		cellList = new CellList<MenuUi>(new MenuCell(), new CellListStyle());
		cellList.setRowData(data);
		panel.add(cellList);
		selectionModel = new SingleSelectionModel<MenuUi>();
		cellList.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

					public void onSelectionChange(SelectionChangeEvent pEvent) {
						buildEditionPanel(selectionModel.getSelectedObject());

					}
				});

		editionPanel = new SimplePanel();
		editionPanel.setStyleName(CSS.userSpaceContentEdition());
		panel.add(editionPanel);

		updateButton = new Button("Modifier");
		documentUpdateButton = new Button("Mettre à jour le document");
		documentDeleteButton = new Button("Supprimer le document");
	}

	private void buildEditionPanel(MenuUi pMenuUi) {
		FlexTable lPanel = new FlexTable();
		
		int lRowIndex = 0;
		
		//Menu title
		menuTitleInput = new TextBox();
		menuTitleInput.setWidth("300px");
		menuTitleInput.setValue(pMenuUi.getTitle());
		lPanel.setWidget(lRowIndex, 0, createLabel("Intitulé du menu"));
		lPanel.setWidget(lRowIndex, 1, menuTitleInput);
		lRowIndex++;
		
		//Content edition
		ContentUI lContentUI = pMenuUi.getContentSet().get(0);
		// Summary
		summaryInput = new TextBox();
		summaryInput.setWidth("300px");
		summaryInput.setValue(lContentUI.getSummary());
		lPanel.setWidget(lRowIndex, 0, createLabel("Résumé"));
		lPanel.setWidget(lRowIndex, 1, summaryInput);
		lRowIndex++;

		// Content
		CKConfig lConfig = new CKConfig();
		Toolbar lToolbar = new Toolbar();
		ToolbarLine lToolbarLine = new ToolbarLine();
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.Bold);
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.Blockquote);
		lToolbar.add(lToolbarLine);
		lConfig.setToolbar(lToolbar);
		contentInput = new CKEditor(lConfig);
		contentInput.setHTML(new String(lContentUI.getData()));
		lPanel.setWidget(lRowIndex, 0, createLabel("Contenu"));
		lPanel.setWidget(lRowIndex, 1, contentInput);

		contentInput.setHTML(new String(lContentUI.getData()));
		lRowIndex++;
		
		buildDocumentPanel(pMenuUi);
		lPanel.setWidget(lRowIndex, 0, createLabel("Documents attachés"));
		lPanel.setWidget(lRowIndex, 1, documentPanel);
		lRowIndex++;
		
		lPanel.setWidget(lRowIndex, 0, updateButton);
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lCellFormatter.setColSpan(lRowIndex, 0, 2);
		lCellFormatter.setHorizontalAlignment(lRowIndex, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		
		editionPanel.setWidget(lPanel);
	}
	
	private void buildDocumentPanel(final MenuUi pMenuUi) {
		documentPanel = new HorizontalPanel();
		documentCellList = new CellList<DocumentUi>(new DocumentCell(), new CellListStyle());
		documentCellList.setRowData(pMenuUi.getDocumentSet());
		documentPanel.add(documentCellList);
		documentSelectionModel = new SingleSelectionModel<DocumentUi>();
		documentCellList.setSelectionModel(documentSelectionModel);
		documentSelectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

					public void onSelectionChange(SelectionChangeEvent pEvent) {
						buildDocumentEditionPanel(documentSelectionModel.getSelectedObject(), pMenuUi.getId());

					}
				});
		documentEditionPanel = new SimplePanel();
		documentEditionPanel.setStyleName(CSS.userSpaceContentEdition());
		documentEditionPanel.setWidget(new DocumentWidget(pMenuUi.getId()));
		documentPanel.add(documentEditionPanel);
		
	}
	
	private void buildDocumentEditionPanel(final DocumentUi pDocument, final Long pMenu) {
		FlexTable lPanel = new FlexTable();
		int lRowIndex = 0;
		
		//Title
		documentTitleInput = new TextBox();
		documentTitleInput.setValue(pDocument.getTitle());
		lPanel.setWidget(lRowIndex, 0, createLabel("Titre"));
		lPanel.setWidget(lRowIndex, 1, documentTitleInput);
		lRowIndex++;
		
		//Summary
		documentSummaryInput = new TextBox();
		documentSummaryInput.setValue(pDocument.getSummary());
		lPanel.setWidget(lRowIndex, 0, createLabel("Résumé"));
		lPanel.setWidget(lRowIndex, 1, documentSummaryInput);
		lRowIndex++;
		
		lPanel.setWidget(lRowIndex, 0, documentUpdateButton);
		lPanel.setWidget(lRowIndex, 1, documentDeleteButton);
		
		PopupPanel lPopup = new PopupPanel(true, true);
		lPopup.setWidget(lPanel);
		lPopup.center();
	}

	
	public Long getContentId() {
		return selectionModel.getSelectedObject().getContentSet().get(0).getId();
	}

	public HasValue<String> getSummary() {
		return summaryInput;
	}

	public String getContent() {
		return contentInput.getHTML();
	}

	public HasClickHandlers getUpdateButton() {
		return updateButton;
	}

	public HasValue<String> getMenuTitle() {
		return menuTitleInput;
	}
	
	private Label createLabel(String pLabel) {
		Label lLabel = new Label(pLabel);
		lLabel.setStyleName(CSS.userSpaceContentLabel());
		return lLabel;
	}

	public HasClickHandlers getDocumentUpdateButton() {
		return documentUpdateButton;
	}

	public HasClickHandlers getDocumentDeleteButton() {
		return documentDeleteButton;
	}

	public Long getDocumentId() {
		return documentSelectionModel.getSelectedObject().getId();
	}

	public HasValue<String> getDocumentTitle() {
		return documentTitleInput;
	}

	public HasValue<String> getDocumentSummary() {
		return documentSummaryInput;
	}
}