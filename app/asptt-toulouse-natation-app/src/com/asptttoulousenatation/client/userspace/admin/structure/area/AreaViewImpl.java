package com.asptttoulousenatation.client.userspace.admin.structure.area;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.ArrayList;

import com.asptttoulousenatation.client.userspace.admin.structure.menu.ui.MenuTreeViewModel;
import com.asptttoulousenatation.client.userspace.admin.ui.DocumentCell;
import com.asptttoulousenatation.client.userspace.admin.util.CellListStyle;
import com.asptttoulousenatation.client.userspace.document.DocumentWidget;
import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.client.ui.EditorToolbar;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentUI;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.AbstractCellTree;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

public class AreaViewImpl extends Composite implements AreaView {

	private AreaUi area;
	private VerticalPanel panel;
	private VerticalPanel menuPanel;

	private AbstractCellTree cellBrowser;
	private SingleSelectionModel<MenuUi> selectionModel;
	private SelectionChangeEvent.Handler pageSelectionAction;
	private SimplePanel editionPanel;

	private TextBox menuTitleInput;
	private TextBox summaryInput;
	private CKEditor contentInput;
	private Button updateButton;
	private Button deleteButton;

	// Document edition
	private HorizontalPanel documentPanel;
	private CellList<DocumentUi> documentCellList;
	private SingleSelectionModel<DocumentUi> documentSelectionModel;
	private SimplePanel documentEditionPanel;
	private TextBox documentTitleInput;
	private TextBox documentSummaryInput;
	private Button documentUpdateButton;
	private Button documentDeleteButton;

	// Area creation
	private Button updateAreaButton;
	private Button deleteAreaButton;
	private TextBox areaTitle;
	private TextBox areaOrder;
	
	//Menu creation
	private SimplePanel menuCreationPanel;

	private TextBox menuCreationTitleInput;
	private TextBox menuCreationSummaryInput;
	private CKEditor menuCreationContentInput;
	private Button menuCreationButton;
	private Button menuCreationCloseButton;
	private ListBox menuCreationMenuKey;
	
	private PopupPanel menuCreationPopup;
	
	//Sub menu creation
	private Button subMenuCreationButton;
	private TextBox subMenuCreationTitleInput;
	private Long parentId;

	public AreaViewImpl(AreaUi pArea) {
		area = pArea;
		panel = new VerticalPanel();
		initWidget(panel);

		createAreaEdition();

		menuPanel = new VerticalPanel();
		menuPanel.addStyleName(CSS.userSpaceAreaMenuSelection());
		Label lMenuPanelTitle = new Label("Editer les pages");
		lMenuPanelTitle.addStyleName(CSS.userSpaceAreaMenuSelectionTitle());
		menuPanel.add(lMenuPanelTitle);
		panel.add(menuPanel);

		selectionModel = new SingleSelectionModel<MenuUi>();

		cellBrowser = new CellBrowser(new MenuTreeViewModel(selectionModel, new ArrayList<MenuUi>(area.getMenuSet().values())), null);
		cellBrowser.setWidth("400px");
		cellBrowser.setHeight("300px");
		menuPanel.add(cellBrowser);
		
		editionPanel = new SimplePanel();
		editionPanel.setStyleName(CSS.userSpaceContentEdition());
		panel.add(editionPanel);

		updateButton = new Button("");
		updateButton.setTitle("Modifier le menu");
		deleteButton = new Button("");
		deleteButton.setTitle("Supprimer le menu");
		documentUpdateButton = new Button("Mettre à jour le document");
		documentDeleteButton = new Button("Supprimer le document");
		menuCreationButton = new Button("");
		menuCreationButton.setTitle("Ajouter une page");
		menuCreationButton.setStyleName(CSS.newPageButton());
		
		menuCreationCloseButton = new Button();
		menuCreationCloseButton.setTitle("Fermer");
		menuCreationCloseButton.setStyleName(CSS.popupCloseButton());
		menuCreationCloseButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				menuCreationPopup.hide();
			}
		});
	}

	public void buildEditionPanel(MenuUi pMenuUi) {
		FlexTable lPanel = new FlexTable();

		int lRowIndex = 0;
		
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lPanel.setHTML(lRowIndex, 0, "Editer " + pMenuUi.getTitle());
		lCellFormatter.setStyleName(lRowIndex, 0, CSS.userSpaceAreaEditionEditTitle());
		lCellFormatter.setColSpan(lRowIndex, 0, 5);
		lCellFormatter.setHorizontalAlignment(lRowIndex, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		lRowIndex++;
		
		//Add sub menu
		if (pMenuUi.getParentId() == null) {
			Button lAddSubMenuButton = new Button("Ajouter un sous-menu");
			lAddSubMenuButton.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent pEvent) {
					parentId = selectionModel.getSelectedObject().getId();
					createMenuCreationPanel();
				}
			});
			lPanel.setWidget(lRowIndex, 0, lAddSubMenuButton);
			lRowIndex++;
		} else {
			parentId = null;
		}
		
		//Menu key
		menuCreationMenuKey = new ListBox();
		int i = 0;
		for (MenuItems lItem : MenuItems.getSelectableMenuItems()) {
			menuCreationMenuKey.insertItem(lItem.getI18n(), lItem.name(), i);
			if (pMenuUi.getMenuKey().equals(lItem.name())) {
				menuCreationMenuKey.setSelectedIndex(i);
			}
			i++;
		}
		lPanel.setWidget(lRowIndex, 0, createLabel("Clé du menu (non obligatoire)"));
		lPanel.setWidget(lRowIndex, 1, menuCreationMenuKey);
		lRowIndex++;

		// Menu title
		menuTitleInput = new TextBox();
		menuTitleInput.setWidth("300px");
		menuTitleInput.setValue(pMenuUi.getTitle());
		lPanel.setWidget(lRowIndex, 0, createLabel("Titre de la page"));
		lPanel.setWidget(lRowIndex, 1, menuTitleInput);
		lRowIndex++;

		// Content edition
		final ContentUI lContentUI;
		if(CollectionUtils.isNotEmpty(pMenuUi.getContentSet())){
			lContentUI = pMenuUi.getContentSet().get(0);
		} else {
			lContentUI = new ContentUI();
			lContentUI.setId(pMenuUi.getId());
			lContentUI.setData(new byte[0]);
		}
		// Summary
		summaryInput = new TextBox();
		summaryInput.setWidth("300px");
		summaryInput.setValue(lContentUI.getSummary());
		lPanel.setWidget(lRowIndex, 0, createLabel("Résumé"));
		lPanel.setWidget(lRowIndex, 1, summaryInput);
		lRowIndex++;

		// Content
		contentInput = new CKEditor(new EditorToolbar());
		contentInput.setHTML(new String(lContentUI.getData()));
		lPanel.setWidget(lRowIndex, 0, createLabel("Contenu"));
		lPanel.setWidget(lRowIndex, 1, contentInput);

		contentInput.setHTML(new String(lContentUI.getData()));
		lRowIndex++;

		buildDocumentPanel(pMenuUi);
		lPanel.setWidget(lRowIndex, 0, createLabel("Documents attachés"));
		lPanel.setWidget(lRowIndex, 1, documentPanel);
		lRowIndex++;

		HorizontalPanel lButtonBar = new HorizontalPanel();
		lButtonBar.setStyleName(CSS.buttonBar());
		lButtonBar.add(updateButton);
		lButtonBar.add(deleteButton);
		updateButton.setStyleName(CSS.editButton());
		deleteButton.setStyleName(CSS.deleteButton());
		lPanel.setWidget(lRowIndex, 0, lButtonBar);
		lCellFormatter = lPanel.getFlexCellFormatter();
		lCellFormatter.setColSpan(lRowIndex, 0, 2);
		lCellFormatter.setHorizontalAlignment(lRowIndex, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		editionPanel.setWidget(lPanel);
	}

	private void buildDocumentPanel(final MenuUi pMenuUi) {
		documentPanel = new HorizontalPanel();
		documentCellList = new CellList<DocumentUi>(new DocumentCell(),
				new CellListStyle());
		documentCellList.setRowData(pMenuUi.getDocumentSet());
		documentPanel.add(documentCellList);
		documentSelectionModel = new SingleSelectionModel<DocumentUi>();
		documentCellList.setSelectionModel(documentSelectionModel);
		documentSelectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

					public void onSelectionChange(SelectionChangeEvent pEvent) {
						buildDocumentEditionPanel(
								documentSelectionModel.getSelectedObject(),
								pMenuUi.getId());

					}
				});
		documentEditionPanel = new SimplePanel();
		documentEditionPanel.setStyleName(CSS.userSpaceContentEdition());
		documentEditionPanel.setWidget(new DocumentWidget(pMenuUi.getId()));
		documentPanel.add(documentEditionPanel);
	}

	private void buildDocumentEditionPanel(final DocumentUi pDocument,
			final Long pMenu) {
		FlexTable lPanel = new FlexTable();
		int lRowIndex = 0;

		// Title
		documentTitleInput = new TextBox();
		documentTitleInput.setValue(pDocument.getTitle());
		lPanel.setWidget(lRowIndex, 0, createLabel("Titre"));
		lPanel.setWidget(lRowIndex, 1, documentTitleInput);
		lRowIndex++;

		// Summary
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

	private void createAreaEdition() {
		FlexTable lPanel = new FlexTable();
		int lRowIndex = 0;

		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lPanel.setHTML(lRowIndex, 0, "Editer le menu");
		lCellFormatter.setStyleName(lRowIndex, 0, CSS.userSpaceAreaEditionEditTitle());
		lCellFormatter.setColSpan(lRowIndex, 0, 5);
		lCellFormatter.setHorizontalAlignment(lRowIndex, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		lRowIndex++;

		// Area title
		areaTitle = new TextBox();
		areaTitle.setWidth("200px");
		areaTitle.setValue(area.getTitle());
		lPanel.setWidget(lRowIndex, 0, createLabel("Intitulé du menu"));
		lPanel.setWidget(lRowIndex, 1, areaTitle);

		// Area order
		areaOrder = new TextBox();
		areaOrder.setWidth("30px");
		areaOrder.setValue(Short.toString(area.getOrder()));
		lPanel.setWidget(lRowIndex, 2, createLabel("Ordre d'affichage"));
		lPanel.setWidget(lRowIndex, 3, areaOrder);

		
		
		HorizontalPanel lButtonBar = new HorizontalPanel();
		lButtonBar.setStyleName(CSS.buttonBar());
		updateAreaButton = new Button("");
		updateAreaButton.setTitle("Mettre à jour le menu");
		deleteAreaButton = new Button("");
		deleteAreaButton.setTitle("Supprimer le menu");
		lButtonBar.add(updateAreaButton);
		lButtonBar.add(deleteAreaButton);
		lButtonBar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lButtonBar.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		updateAreaButton.setStyleName(CSS.editButton());
		deleteAreaButton.setStyleName(CSS.deleteButton());
		lPanel.setWidget(lRowIndex, 4, lButtonBar);
		
		lRowIndex++;
		//Menu button
		Button lAddMenuButton = new Button("Ajouter une page");
		lAddMenuButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				createMenuCreationPanel();
			}
		});
		lPanel.setWidget(lRowIndex, 0, lAddMenuButton);
		lPanel.addStyleName(CSS.userSpaceAreaEditionEdit());
		panel.add(lPanel);
	}
	
	private void createMenuCreationPanel() {
			FlexTable lPanel = new FlexTable();
			int lRowIndex = 0;
			
			FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
			lPanel.setHTML(lRowIndex, 0, "Ajouter une page");
			lCellFormatter.setColSpan(lRowIndex, 0, 5);
			lCellFormatter.setHorizontalAlignment(lRowIndex, 0,
					HasHorizontalAlignment.ALIGN_CENTER);
			lRowIndex++;
			
			//Menu key
			menuCreationMenuKey = new ListBox();
			int i = 0;
			for (MenuItems lItem : MenuItems.getSelectableMenuItems()) {
				menuCreationMenuKey.insertItem(lItem.getI18n(), lItem.name(), i);
				i++;
			}
			lPanel.setWidget(lRowIndex, 0, createLabel("Clé du menu (non obligatoire)"));
			lPanel.setWidget(lRowIndex, 1, menuCreationMenuKey);
			lRowIndex++;

			// Menu title
			menuCreationTitleInput = new TextBox();
			menuCreationTitleInput.setWidth("300px");
			lPanel.setWidget(lRowIndex, 0, createLabel("Intitulé de la page (et du sous-menu)"));
			lPanel.setWidget(lRowIndex, 1, menuCreationTitleInput);
			lRowIndex++;

			// Summary
			menuCreationSummaryInput = new TextBox();
			menuCreationSummaryInput.setWidth("300px");
			lPanel.setWidget(lRowIndex, 0, createLabel("Résumé"));
			lPanel.setWidget(lRowIndex, 1, menuCreationSummaryInput);
			lRowIndex++;

			// Content
			menuCreationContentInput = new CKEditor(new EditorToolbar());
			lPanel.setWidget(lRowIndex, 0, createLabel("Contenu"));
			lPanel.setWidget(lRowIndex, 1, menuCreationContentInput);

			lRowIndex++;

			HorizontalPanel lButtonBar = new HorizontalPanel();
			lButtonBar.setStyleName(CSS.buttonBar());
			lButtonBar.add(menuCreationButton);
			lButtonBar.add(menuCreationCloseButton);
			lButtonBar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			lButtonBar.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			updateAreaButton.setStyleName(CSS.editButton());
			deleteAreaButton.setStyleName(CSS.deleteButton());
			lCellFormatter.setColSpan(lRowIndex, 0, 2);
			lCellFormatter.setHorizontalAlignment(lRowIndex, 0, HasHorizontalAlignment.ALIGN_CENTER);
			lPanel.setWidget(lRowIndex, 0, lButtonBar);
			
			menuCreationPanel = new SimplePanel();
			menuCreationPanel.setWidget(lPanel);
			menuCreationPopup = new PopupPanel(false, true);
			menuCreationPopup.setWidget(lPanel);
			menuCreationPopup.center();
	}
	
	public Long getContentId() {
		MenuUi lMenu = selectionModel.getSelectedObject();
		return lMenu.getContentSet().get(0)
				.getId();
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

	public HasClickHandlers getAreaUpdateButton() {
		return updateAreaButton;
	}

	public HasValue<String> getAreaTitle() {
		return areaTitle;
	}

	public Short getAreaOrder() {
		return Short.valueOf(areaOrder.getValue());
	}

	public HasClickHandlers getMenuCreationButton() {
		return menuCreationButton;
	}

	public HasValue<String> getMenuCreationTitle() {
		return menuCreationTitleInput;
	}

	public HasValue<String> getMenuCreationSummary() {
		return menuCreationSummaryInput;
	}

	public String getMenuCreationContent() {
		return menuCreationContentInput.getHTML();
	}

	public HasClickHandlers getDeleteButton() {
		return deleteButton;
	}

	public HasClickHandlers getAreaDeleteButton() {
		return deleteAreaButton;
	}

	public Long getMenuId() {
		return selectionModel.getSelectedObject().getId();
	}
	
	public void hideMenuCreationPopup() {
		if(menuCreationPopup != null) {
			menuCreationPopup.hide();
			parentId = null;
		}
	}

	public Long getParentMenuId() {
		return parentId;
	}

	public HasClickHandlers getSubMenuCreationButton() {
		// TODO Auto-generated method stub
		return null;
	}

	public HasValue<String> getSubMenuCreationTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMenuCreationMenuKey() {
		return menuCreationMenuKey.getValue(menuCreationMenuKey.getSelectedIndex());
	}

	public void setPageSelectionAction(Handler pHandler) {
		selectionModel.addSelectionChangeHandler(pHandler);
	}
}