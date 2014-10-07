package com.asptttoulousenatation.client.userspace.admin.actu;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.client.userspace.admin.ui.DocumentCell;
import com.asptttoulousenatation.client.userspace.admin.util.CellListStyle;
import com.asptttoulousenatation.client.userspace.document.DocumentWidget;
import com.asptttoulousenatation.core.client.ui.EditorToolbar;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Style;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class ActuEditionViewImpl extends Composite implements ActuEditionView {

	private HorizontalPanel panel;
	private List<ActuUi> data;
	private CellList<ActuUi> cellList;
	private SingleSelectionModel<ActuUi> selectionModel;
	private SimplePanel actuEditionPanel;

	private TextBox title;
	private TextBox summary;
	private DateBox date;
	private TextBox imageUrl;
	private CheckBox competition;
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

	public ActuEditionViewImpl(List<ActuUi> pData) {
		data = pData;
		panel = new HorizontalPanel();
		initWidget(panel);
		cellList = new CellList<ActuUi>(new ActuCell(), new CellResources());
		cellList.setRowData(data);
		panel.add(cellList);
		selectionModel = new SingleSelectionModel<ActuUi>();
		cellList.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

					public void onSelectionChange(SelectionChangeEvent pEvent) {
						buildActuEditionPanel(selectionModel
								.getSelectedObject());

					}
				});

		actuEditionPanel = new SimplePanel();
		actuEditionPanel.setStyleName(CSS.userSpaceAreaEdition());
		panel.add(actuEditionPanel);

		updateButton = new Button("Modifier");
		deleteButton = new Button("");
		updateButton = new Button("");
		updateButton.setTitle("Modifier l'actualité");
		deleteButton = new Button("");
		deleteButton.setTitle("Supprimer l'actualité");

	}

	private void buildDocumentPanel(final ActuUi pUi) {
		documentPanel = new HorizontalPanel();
		documentCellList = new CellList<DocumentUi>(new DocumentCell(),
				new CellListStyle());
		documentCellList.setRowData(pUi.getDocumentSet());
		documentPanel.add(documentCellList);
		documentSelectionModel = new SingleSelectionModel<DocumentUi>();
		documentCellList.setSelectionModel(documentSelectionModel);
		documentSelectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

					public void onSelectionChange(SelectionChangeEvent pEvent) {
						buildDocumentEditionPanel(
								documentSelectionModel.getSelectedObject(),
								pUi.getId());

					}
				});
		documentEditionPanel = new SimplePanel();
		documentEditionPanel.setStyleName(CSS.userSpaceContentEdition());
		documentEditionPanel.setWidget(new DocumentWidget(pUi.getId()));
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

	public Widget asWidget() {
		return this;
	}

	public HasClickHandlers getUpdateButton() {
		return updateButton;
	}

	private void buildActuEditionPanel(ActuUi pActuUi) {
		// Input
		FlexTable lPanel = new FlexTable();
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		
		int rowIndex = 0;
		// Title
		title = new TextBox();
		title.setWidth("300px");
		title.setValue(pActuUi.getTitle());
		lPanel.setWidget(rowIndex, 0, createLabel("Titre"));
		lPanel.setWidget(rowIndex, 1, title);
		rowIndex++;

		// Summary
				summary = new TextBox();
				summary.setWidth("300px");
				summary.setValue(pActuUi.getSummary());
				lPanel.setWidget(rowIndex, 0, createLabel("Description courte"));
				lPanel.setWidget(rowIndex, 1, summary);
				rowIndex++;
				
		// Date
		date = new DateBox();
		date.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd MMMM yyyy HH:mm:ss")));
		date.setWidth("200px");
		date.setValue(pActuUi.getCreationDate());
		lPanel.setWidget(rowIndex, 0, createLabel("Date"));
		lPanel.setWidget(rowIndex, 1, date);
		rowIndex++;
		
		//Image url
		imageUrl = new TextBox();
		imageUrl.setWidth("300px");
		imageUrl.setValue(pActuUi.getImageUrl());
		lPanel.setWidget(rowIndex, 0, createLabel("URL de l'image de fond"));
		lPanel.setWidget(rowIndex, 1, imageUrl);
		rowIndex++;
		
		//Compétition
		competition = new CheckBox();
		competition.setValue(pActuUi.isCompetition());
		lPanel.setWidget(rowIndex, 0, createLabel("Actu compétition ?"));
		lPanel.setWidget(rowIndex, 1, competition);
		rowIndex++;

		// Contenu
		// Add the components to a panel
		// Content
		contentInput = new CKEditor(new EditorToolbar());
		contentInput.setHTML(pActuUi.getContent());
		lPanel.setWidget(rowIndex, 0, createLabel("Actualité"));
		lPanel.setWidget(rowIndex, 1, contentInput);
		rowIndex++;
		
		buildDocumentPanel(pActuUi);
		lPanel.setWidget(rowIndex, 0, createLabel("Documents attachés"));
		lPanel.setWidget(rowIndex, 1, documentPanel);
		rowIndex++;

		HorizontalPanel lButtonBar = new HorizontalPanel();
		lButtonBar.add(updateButton);
		lButtonBar.add(deleteButton);
		updateButton.setStyleName(CSS.editButton());
		deleteButton.setStyleName(CSS.deleteButton());
		lPanel.setWidget(rowIndex, 0, lButtonBar);
		lCellFormatter.setColSpan(rowIndex, 0, 2);
		lCellFormatter.setHorizontalAlignment(rowIndex, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		actuEditionPanel.setWidget(lPanel);
	}

	public Long getActu() {
		return selectionModel.getSelectedObject().getId();
	}

	public HasValue<String> getTitre() {
		return title;
	}

	public HasValue<String> getSummary() {
		return summary;
	}
	
	public String getContent() {
		return contentInput.getHTML();
	}

	public HasValue<Date> getCreationDate() {
		return date;
	}

	public class CellResources implements CellList.Resources {

		public ImageResource cellListSelectedBackground() {
			// TODO Auto-generated method stub
			return null;
		}

		public Style cellListStyle() {
			return new Style() {

				public boolean ensureInjected() {
					return false;
				}

				public String getText() {
					return "";
				}

				public String getName() {
					return "";
				}

				public String cellListEvenItem() {
					return "";
				}

				public String cellListKeyboardSelectedItem() {
					return CSS.userSpaceContentListSelected();
				}

				public String cellListOddItem() {
					return CSS.userSpaceContentListOdd();
				}

				public String cellListSelectedItem() {
					return CSS.userSpaceContentListSelected();
				}

				public String cellListWidget() {
					return CSS.userSpaceContentList();
				}
			};
		}

	}

	private Label createLabel(String pLabel) {
		Label lLabel = new Label(pLabel);
		lLabel.setStyleName(CSS.userSpaceContentLabel());
		return lLabel;
	}

	public HasClickHandlers getDeleteButton() {
		return deleteButton;
	}

	@Override
	public HasValue<String> getImageUrl() {
		return imageUrl;
	}

	@Override
	public HasValue<Boolean> getCompetition() {
		return competition;
	}
}