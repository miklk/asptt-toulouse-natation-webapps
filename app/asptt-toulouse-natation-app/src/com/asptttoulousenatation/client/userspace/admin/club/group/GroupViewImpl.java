package com.asptttoulousenatation.client.userspace.admin.club.group;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.List;

import com.asptttoulousenatation.client.userspace.admin.util.CellListStyle;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class GroupViewImpl extends Composite implements GroupView {

	private List<GroupUi> data;
	private HorizontalPanel panel;
	
	private CellList<GroupUi> cellList;
	private SingleSelectionModel<GroupUi> selectionModel;
	private SimplePanel editionPanel;
	
	private Button createButton;
	private Button updateButton;
	
	private TextBox groupTitle;
	
	public GroupViewImpl(List<GroupUi> pGroups) {
		data = pGroups;
		panel = new HorizontalPanel();
		initWidget(panel);
		
		cellList = new CellList<GroupUi>(new GroupCell(), new CellListStyle());
		cellList.setRowData(data);
		panel.add(cellList);
		selectionModel = new SingleSelectionModel<GroupUi>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			public void onSelectionChange(SelectionChangeEvent pEvent) {
				buildEditionPanel(selectionModel.getSelectedObject());
				
			}
		});
		editionPanel = new SimplePanel();
		editionPanel.setStyleName(CSS.userSpaceContentEdition());
		panel.add(editionPanel);
		
		createButton = new Button("Créer");
		updateButton = new Button("Mettre à jour");
		updateButton.setEnabled(false);
		
		buildCreationPanel();
	}
	
	private void buildEditionPanel(GroupUi pGroupUi) {
		groupTitle.setValue(pGroupUi.getTitle());
		updateButton.setEnabled(true);
	}
	
	private void buildCreationPanel() {
		FlexTable lPanel = new FlexTable();
		
		//Input
		int lRowIndex = 0;
		//Title
		groupTitle = new TextBox();
		groupTitle.setWidth("200px");
		lPanel.setWidget(lRowIndex, 0, createLabel("Nom"));
		lPanel.setWidget(lRowIndex, 1, groupTitle);
		lRowIndex++;
		
		lPanel.setWidget(5, 0, updateButton);
		lPanel.setWidget(5, 1, createButton);
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lCellFormatter.setHorizontalAlignment(3, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		editionPanel.clear();
		editionPanel.setWidget(lPanel);
	}
	
	public HasValue<String> getGroupTitle() {
		return groupTitle;
	}

	public HasClickHandlers getCreateButton() {
		return createButton;
	}

	public HasClickHandlers getUpdateButton() {
		return updateButton;
	}

	public Long getGroupId() {
		return selectionModel.getSelectedObject().getId();
	}
	
	private Label createLabel(String pLabel) {
		Label lLabel = new Label(pLabel);
		lLabel.setStyleName(CSS.userSpaceContentLabel());
		return lLabel;
	}
}