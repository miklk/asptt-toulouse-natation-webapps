package com.asptttoulousenatation.client.userspace.admin.club.group;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.List;

import com.asptttoulousenatation.client.userspace.admin.util.CellListStyle;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
	private Button deleteButton;

	private TextBox groupTitle;
	private CheckBox groupLicenceFfn;

	public GroupViewImpl(List<GroupUi> pGroups) {
		data = pGroups;
		panel = new HorizontalPanel();
		initWidget(panel);

		cellList = new CellList<GroupUi>(new GroupCell(), new CellListStyle());
		cellList.setRowData(data);
		panel.add(cellList);
		selectionModel = new SingleSelectionModel<GroupUi>();
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

		createButton = new Button("");
		createButton.setStyleName(CSS.addButton());
		updateButton = new Button("");
		updateButton.setStyleName(CSS.editButton());
		deleteButton = new Button("");
		deleteButton.setStyleName(CSS.deleteButton());
		updateButton.setVisible(false);
		deleteButton.setVisible(false);

		buildCreationPanel();
	}

	private void buildEditionPanel(GroupUi pGroupUi) {
		groupTitle.setValue(pGroupUi.getTitle());
		groupLicenceFfn.setValue(pGroupUi.isLicenceFfn());
		updateButton.setVisible(true);
		deleteButton.setVisible(true);
	}

	private void buildCreationPanel() {
		FlexTable lPanel = new FlexTable();

		// Input
		int lRowIndex = 0;
		// Title
		groupTitle = new TextBox();
		groupTitle.setWidth("200px");
		lPanel.setWidget(lRowIndex, 0, createLabel("Nom"));
		lPanel.setWidget(lRowIndex, 1, groupTitle);
		lRowIndex++;

		// Licence FFN
		groupLicenceFfn = new CheckBox();
		lPanel.setWidget(lRowIndex, 0, createLabel("Licence FFN"));
		lPanel.setWidget(lRowIndex, 1, groupLicenceFfn);
		lRowIndex++;

		HorizontalPanel lButtonBar = new HorizontalPanel();
		lButtonBar.setStyleName(CSS.buttonBar());
		lButtonBar.add(updateButton);
		lButtonBar.add(deleteButton);
		lButtonBar.add(createButton);
		lPanel.setWidget(lRowIndex, 0, lButtonBar);
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lCellFormatter.setColSpan(lRowIndex, 0, 2);
		lCellFormatter.setHorizontalAlignment(lRowIndex, 0,
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

	public HasClickHandlers getDeleteButton() {
		return deleteButton;
	}

	public HasValue<Boolean> getGroupLicenceFfn() {
		return groupLicenceFfn;
	}
}