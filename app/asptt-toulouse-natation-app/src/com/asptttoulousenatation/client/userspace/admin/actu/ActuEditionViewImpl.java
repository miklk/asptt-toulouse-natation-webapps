package com.asptttoulousenatation.client.userspace.admin.actu;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.core.client.ui.EditorToolbar;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Style;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
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
	private CKEditor contentInput;
	private Button updateButton;
	private Button deleteButton;
	
	public ActuEditionViewImpl(List<ActuUi> pData) {
		data = pData;
		panel = new HorizontalPanel();
		initWidget(panel);
		cellList = new CellList<ActuUi>(new ActuCell(), new CellResources());
		cellList.setRowData(data);
		panel.add(cellList);
		selectionModel = new SingleSelectionModel<ActuUi>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			public void onSelectionChange(SelectionChangeEvent pEvent) {
				buildActuEditionPanel(selectionModel.getSelectedObject());
				
			}
		});
		
		actuEditionPanel = new SimplePanel();
		actuEditionPanel.setStyleName(CSS.userSpaceAreaEdition());
		panel.add(actuEditionPanel);
		
		updateButton = new Button("Modifier");
		deleteButton = new Button("");
	}
	
	public Widget asWidget() {
		return this;
	}

	public HasClickHandlers getUpdateButton() {
		return updateButton;
	}
	
	private void buildActuEditionPanel(ActuUi pActuUi) {
		//Input
		FlexTable lPanel = new FlexTable();
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		//Title
		title = new TextBox();
		title.setWidth("300px");
		title.setValue(pActuUi.getTitle());
		lPanel.setWidget(0, 0, createLabel("Titre"));
		lPanel.setWidget(0, 1, title);
		
		//Summary
		summary = new TextBox();
		summary.setWidth("300px");
		summary.setValue(pActuUi.getSummary());
		lPanel.setWidget(1, 0, createLabel("Description courte"));
		lPanel.setWidget(1, 1, summary);
		
		//Date
		date = new DateBox();
		date.setWidth("200px");
		date.setValue(pActuUi.getCreationDate());
		lPanel.setWidget(2, 0, createLabel("Date"));
		lPanel.setWidget(2, 1, date);
		
		//Contenu
	    // Add the components to a panel
		// Content
		contentInput = new CKEditor(new EditorToolbar());
		contentInput.setHTML(pActuUi.getContent());
		lPanel.setWidget(3, 0, createLabel("Actualité"));
		lPanel.setWidget(3, 1, contentInput);

		HorizontalPanel lButtonBar = new HorizontalPanel();
		lButtonBar.add(updateButton);
		lButtonBar.add(deleteButton);
		updateButton.setWidth("300px");
		updateButton.setHeight("50px");
		deleteButton.setStyleName(CSS.deleteButton());
		lPanel.setWidget(4, 0, lButtonBar);
		lCellFormatter.setColSpan(4, 0, 2);
		lCellFormatter.setHorizontalAlignment(4, 0,
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
}