package com.asptttoulousenatation.client.userspace.admin.competition;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asptttoulousenatation.core.shared.competition.CompetitionDayUi;
import com.asptttoulousenatation.core.shared.competition.CompetitionUi;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class CompetitionViewImpl extends ResizeComposite implements
		CompetitionView {

	private List<CompetitionUi> data;
	private List<CompetitionDayUi> dayData;

	private LayoutPanel panel;

	private CellList<CompetitionUi> cellList;
	private SingleSelectionModel<CompetitionUi> selectionModel;
	private SimpleLayoutPanel editionPanel;

	private Button createButton;
	private Button updateButton;
	
	private TextBox competitionSaison;
	private TextBox competitionTitle;
	private TextBox competitionPlace;
	private DateBox competitionBegin;
	private DateBox competitionEnd;
	
	//Days
	private CellList<CompetitionDayUi> dayCellList;
	private SingleSelectionModel<CompetitionDayUi> daySelectionModel;
	private SimpleLayoutPanel dayEditionPanel;

	private Button dayCreateButton;
	private Button dayUpdateButton;
	
	private TextBox day;
	private DateBox dayBegin;
	private DateBox dayEnd;
	private TextBox needed;
	
	private Map<Long, CompetitionDayUi> createDays;
	private Map<Long, CompetitionDayUi> updateDays;

	public CompetitionViewImpl(List<CompetitionUi> pData) {
		data = pData;
		dayData = new ArrayList<CompetitionDayUi>(0);
		panel = new LayoutPanel();
		initWidget(panel);
		
		cellList = new CellList<CompetitionUi>(new CompetitionCell());
		cellList.setRowData(data);
		panel.add(cellList);
		selectionModel = new SingleSelectionModel<CompetitionUi>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			public void onSelectionChange(SelectionChangeEvent pEvent) {
				buildEditionPanel(selectionModel.getSelectedObject());
				
			}
		});
		editionPanel = new SimpleLayoutPanel();
		panel.add(editionPanel);
		
		createButton = new Button("Créer");
		updateButton = new Button("Mettre à jour");
		updateButton.setEnabled(false);
		
		buildCreationPanel();
		
		cellList.setStyleName(CSS.groupList());
		editionPanel.setStyleName(CSS.groupEdition());
		
		//Layout
		panel.setWidgetLeftWidth(cellList, 0, Unit.PCT, 30, Unit.PCT);
		panel.setWidgetLeftWidth(editionPanel, 32, Unit.PCT, 100, Unit.PCT);
		panel.setWidgetTopHeight(editionPanel, 0, Unit.PCT, 40, Unit.PCT);
		
		createDays = new HashMap<Long, CompetitionDayUi>();
		updateDays = new HashMap<Long, CompetitionDayUi>();
	}
	
	private void buildEditionPanel(CompetitionUi pUi) {
		competitionSaison.setValue(pUi.getSaison());
		competitionTitle.setValue(pUi.getTitle());
		competitionPlace.setValue(pUi.getPlace());
		competitionBegin.setValue(pUi.getBegin());
		competitionEnd.setValue(pUi.getEnd());
		
		dayData = new ArrayList<CompetitionDayUi>(pUi.getDays());
		//Days
		dayCellList.setRowData(dayData);
		updateButton.setEnabled(true);
		
		
	}
	
	private void buildCreationPanel() {
		FlexTable lPanel = new FlexTable();
		lPanel.setCellSpacing(6);

		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lPanel.setHTML(0, 0, "Information compétition");
		lCellFormatter.setColSpan(0, 0, 2);
		lCellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		
		//Input
		//Saison
		competitionSaison = new TextBox();
		lPanel.setHTML(1, 0, "Saison");
		lPanel.setWidget(1, 1, competitionSaison);
		
		//Title
		competitionTitle = new TextBox();
		lPanel.setHTML(2, 0, "Intitulé");
		lPanel.setWidget(2, 1, competitionTitle);
		
		//Place
		competitionPlace = new TextBox();
		lPanel.setHTML(3, 0, "Lieu");
		lPanel.setWidget(3, 1, competitionPlace);
		
		//Begin - end
		competitionBegin = new DateBox();
		lPanel.setHTML(4, 0, "Date");
		lPanel.setWidget(4, 1, competitionBegin);
		competitionEnd = new DateBox();
		lPanel.setWidget(4, 2, competitionEnd);
		
		createDayPanel();
		
		lPanel.setWidget(5, 0, updateButton);
		lPanel.setWidget(5, 2, createButton);
		editionPanel.clear();
		editionPanel.setWidget(lPanel);
	}
	
	private void createDayPanel() {
		LayoutPanel lPanel = new LayoutPanel();
		
		dayCellList = new CellList<CompetitionDayUi>(new CompetitionDayCell());
		dayCellList.setRowData(dayData);
		lPanel.add(dayCellList);
		daySelectionModel = new SingleSelectionModel<CompetitionDayUi>();
		dayCellList.setSelectionModel(daySelectionModel);
		daySelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			public void onSelectionChange(SelectionChangeEvent pEvent) {
				buildDayEditionPanel(daySelectionModel.getSelectedObject());
				
			}
		});
		dayEditionPanel = new SimpleLayoutPanel();
		lPanel.add(dayEditionPanel);
		
		dayCreateButton = new Button("Créer");
		dayCreateButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				CompetitionDayUi lDayUi = new CompetitionDayUi();
				lDayUi.setDay(Integer.valueOf(day.getValue()));
				lDayUi.setBegin(dayBegin.getValue());
				lDayUi.setEnd(dayEnd.getValue());
				lDayUi.setNeeded(Integer.valueOf(needed.getValue()));
				createDays.put(lDayUi.getId(), lDayUi);
			}
		});
		dayUpdateButton = new Button("Mettre à jour");
		dayUpdateButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				CompetitionDayUi lCurrentDay = daySelectionModel.getSelectedObject();
				lCurrentDay.setDay(Integer.valueOf(day.getValue()));
				lCurrentDay.setBegin(dayBegin.getValue());
				lCurrentDay.setEnd(dayEnd.getValue());
				lCurrentDay.setNeeded(Integer.valueOf(needed.getValue()));
				updateDays.put(lCurrentDay.getId(), lCurrentDay);
			}
		});
		dayUpdateButton.setEnabled(false);
		
		buildDayCreationPanel();
		
		dayCellList.setStyleName(CSS.groupList());
		dayEditionPanel.setStyleName(CSS.groupEdition());
		
		//Layout
		lPanel.setWidgetLeftWidth(dayCellList, 0, Unit.PCT, 30, Unit.PCT);
		lPanel.setWidgetLeftWidth(dayEditionPanel, 32, Unit.PCT, 100, Unit.PCT);
		panel.add(lPanel);
		panel.setWidgetLeftWidth(lPanel, 32, Unit.PCT, 100, Unit.PCT);
		panel.setWidgetTopHeight(lPanel, 40, Unit.PCT, 100, Unit.PCT);
	}
	
	private void buildDayEditionPanel(CompetitionDayUi pUi) {
		day.setValue(Integer.toString(pUi.getDay()));
		dayBegin.setValue(pUi.getBegin());
		dayEnd.setValue(pUi.getEnd());
		needed.setValue(Integer.toString(pUi.getNeeded()));
		
		dayUpdateButton.setEnabled(true);
	}
	
	private void buildDayCreationPanel() {
		FlexTable lPanel = new FlexTable();
		lPanel.setCellSpacing(6);

		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lPanel.setHTML(0, 0, "Information sur la journée");
		lCellFormatter.setColSpan(0, 0, 2);
		lCellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		
		//Input
		//Day #
		day = new TextBox();
		lPanel.setHTML(1, 0, "#");
		lPanel.setWidget(1, 1, day);
		
		//Date Begin / End
		dayBegin = new DateBox();
		lPanel.setHTML(2, 0, "Date");
		lPanel.setWidget(2, 1, dayBegin);
		dayEnd = new DateBox();
		lPanel.setWidget(2, 2, dayEnd);
		
		//Needed
		needed = new TextBox();
		lPanel.setHTML(3, 0, "# officiels nécessaires");
		lPanel.setWidget(3, 1, needed);
		
		lPanel.setWidget(4, 0, dayUpdateButton);
		lPanel.setWidget(4, 2, dayCreateButton);
		dayEditionPanel.clear();
		dayEditionPanel.setWidget(lPanel);
	}

	public HasClickHandlers getUpdateButton() {
		return updateButton;
	}

	public HasClickHandlers getCreateButton() {
		return createButton;
	}

	public HasValue<String> getCompetitionTitle() {
		return competitionTitle;
	}

	public HasValue<String> getCompetitionPlace() {
		return competitionPlace;
	}

	public HasValue<Date> getCompetitionBegin() {
		return competitionBegin;
	}

	public HasValue<Date> getCompetitionEnd() {
		return competitionEnd;
	}

	public Set<CompetitionDayUi> getCreateDays() {
		return new HashSet<CompetitionDayUi>(createDays.values());
	}

	public Set<CompetitionDayUi> getUpdateDays() {
		Set<CompetitionDayUi> lUpdate = new HashSet<CompetitionDayUi>();
		for(CompetitionDayUi lDayUi: selectionModel.getSelectedObject().getDays()) {
			if(!updateDays.containsKey(lDayUi.getId()) && !createDays.containsKey(lDayUi.getId())) {
				lUpdate.add(lDayUi);
			}
			
		}
		lUpdate.addAll(updateDays.values());
		lUpdate.addAll(createDays.values());
		return lUpdate;
	}

	public Long getCompetition() {
		return selectionModel.getSelectedObject().getId();
	}

	public HasValue<String> getCompetitionSaison() {
		return competitionSaison;
	}
}