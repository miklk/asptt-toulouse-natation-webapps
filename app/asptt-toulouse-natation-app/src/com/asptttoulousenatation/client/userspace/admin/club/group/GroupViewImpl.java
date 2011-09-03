package com.asptttoulousenatation.client.userspace.admin.club.group;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.List;

import com.asptttoulousenatation.core.client.ui.InputPanel;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
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
		
		cellList = new CellList<GroupUi>(new GroupCell());
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
		panel.add(editionPanel);
		
		createButton = new Button("Créer");
		updateButton = new Button("Mettre à jour");
		updateButton.setEnabled(false);
		
		buildCreationPanel();
		
		cellList.setStyleName(CSS.groupList());
		editionPanel.setStyleName(CSS.groupEdition());
		
		//Layout
//		panel.setWidgetLeftWidth(cellList, 0, Unit.PCT, 30, Unit.PCT);
//		panel.setWidgetLeftWidth(editionPanel, 32, Unit.PCT, 100, Unit.PCT);
	}
	
	private void buildEditionPanel(GroupUi pGroupUi) {
		groupTitle.setValue(pGroupUi.getTitle());
		updateButton.setEnabled(true);
	}
	
	private void buildCreationPanel() {
		HorizontalPanel lPanel = new HorizontalPanel();
		
		//Input
		//Title
		groupTitle = new TextBox();
		InputPanel lGroupTitleInputPanel = new InputPanel("Nom", groupTitle);
		
		lPanel.add(lGroupTitleInputPanel);
		lPanel.add(updateButton);
		lPanel.add(createButton);
//		lPanel.setWidgetTopHeight(lGroupTitleInputPanel, 1, Unit.PCT, 10, Unit.PCT);
//		lPanel.setWidgetLeftWidth(updateButton, 30, Unit.PCT, 20, Unit.PCT);
//		lPanel.setWidgetTopHeight(updateButton, 84, Unit.PCT, 10, Unit.PCT);
//		lPanel.setWidgetLeftWidth(createButton, 50, Unit.PCT, 20, Unit.PCT);
//		lPanel.setWidgetTopHeight(createButton, 84, Unit.PCT, 10, Unit.PCT);
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
}