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
import com.google.gwt.user.client.ui.TextArea;
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
	private CheckBox groupInscription;
	private TextBox groupTarif;
	private TextBox groupTarif2;
	private TextBox groupTarif3;
	private TextBox groupTarif4;
	private TextBox groupTarifWeight;
	private CheckBox seanceUnique;
	private CheckBox nouveau;
	private TextArea description;
	private CheckBox enf;

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
		groupInscription.setValue(pGroupUi.isLicenceFfn());
		groupTarif.setValue("" + pGroupUi.getTarif());
		groupTarif2.setValue("" + pGroupUi.getTarif2());
		groupTarif3.setValue("" + pGroupUi.getTarif3());
		groupTarif4.setValue("" + pGroupUi.getTarif4());
		groupTarifWeight.setValue("" + pGroupUi.getTarifWeight());
		seanceUnique.setValue(pGroupUi.isSeanceunique());
		nouveau.setValue(pGroupUi.isNouveau());
		description.setValue(pGroupUi.getDescription());
		enf.setValue(pGroupUi.isEnf());
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

		// Inscription
		groupInscription = new CheckBox();
		lPanel.setWidget(lRowIndex, 0, createLabel("Valable à l'inscription"));
		lPanel.setWidget(lRowIndex, 1, groupInscription);
		lRowIndex++;
		
		// Tarif
		groupTarif = new TextBox();
		lPanel.setWidget(lRowIndex, 0, createLabel("Tarif"));
		lPanel.setWidget(lRowIndex, 1, groupTarif);
		lRowIndex++;
		
		// Tarif
		groupTarif2 = new TextBox();
		lPanel.setWidget(lRowIndex, 0, createLabel("Tarif 2"));
		lPanel.setWidget(lRowIndex, 1, groupTarif2);
		lRowIndex++;
		// Tarif
		groupTarif3 = new TextBox();
		lPanel.setWidget(lRowIndex, 0, createLabel("Tarif 3"));
		lPanel.setWidget(lRowIndex, 1, groupTarif3);
		lRowIndex++;
		// Tarif
		groupTarif4 = new TextBox();
		lPanel.setWidget(lRowIndex, 0, createLabel("Tarif 4"));
		lPanel.setWidget(lRowIndex, 1, groupTarif4);
		lRowIndex++;
		
		// Tarif weight
		groupTarifWeight = new TextBox();
		lPanel.setWidget(lRowIndex, 0, createLabel("Priorité du tarif"));
		lPanel.setWidget(lRowIndex, 1, groupTarifWeight);
		lRowIndex++;
		
		// Séance unique
		seanceUnique = new CheckBox();
		lPanel.setWidget(lRowIndex, 0, createLabel("1 séance"));
		lPanel.setWidget(lRowIndex, 1, seanceUnique);
		lRowIndex++;
		
		// Nouveau
		nouveau = new CheckBox();
		lPanel.setWidget(lRowIndex, 0, createLabel("Nouveau"));
		lPanel.setWidget(lRowIndex, 1, nouveau);
		lRowIndex++;
		
		// ENF
		enf = new CheckBox();
		lPanel.setWidget(lRowIndex, 0, createLabel("ENF ?"));
		lPanel.setWidget(lRowIndex, 1, enf);
		lRowIndex++;

		// Nouveau
		description = new TextArea();
		lPanel.setWidget(lRowIndex, 0, createLabel("Description"));
		lPanel.setWidget(lRowIndex, 1, description);
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

	public HasValue<Boolean> getGroupInscription() {
		return groupInscription;
	}

	@Override
	public HasValue<String> getGroupTarif() {
		return groupTarif;
	}

	@Override
	public HasValue<String> getGroupTarif2() {
		return groupTarif2;
	}

	@Override
	public HasValue<String> getGroupTarif3() {
		return groupTarif3;
	}

	@Override
	public HasValue<String> getGroupTarif4() {
		return groupTarif4;
	}

	@Override
	public HasValue<Boolean> getSeanceUnique() {
		return seanceUnique;
	}

	@Override
	public HasValue<String> getGroupTarifWeight() {
		return groupTarifWeight;
	}

	@Override
	public HasValue<Boolean> getNouveau() {
		return nouveau;
	}

	@Override
	public HasValue<String> getDescription() {
		return description;
	}

	@Override
	public HasValue<Boolean> getGroupEnf() {
		return enf;
	}
}