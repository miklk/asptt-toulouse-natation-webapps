package com.asptttoulousenatation.client.userspace.admin.competition;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asptttoulousenatation.client.userspace.admin.util.CellListStyle;
import com.asptttoulousenatation.core.shared.competition.CompetitionDayUi;
import com.asptttoulousenatation.core.shared.competition.CompetitionUi;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class CompetitionViewImpl extends Composite implements
		CompetitionView {

	private List<CompetitionUi> data;
	private List<CompetitionDayUi> dayData;

	private Panel panel;
	private Panel competitionPanel;

	private CellList<CompetitionUi> cellList;
	private SingleSelectionModel<CompetitionUi> selectionModel;
	private SimplePanel editionPanel;

	private Button createButton;
	private Button updateButton;
	private Button deleteButton;
	
	private TextBox competitionSaison;
	private TextBox competitionTitle;
	private TextBox competitionPlace;
	private DateBox competitionBegin;
	private DateBox competitionEnd;
	
	//Days
	private CellList<CompetitionDayUi> dayCellList;
	private SingleSelectionModel<CompetitionDayUi> daySelectionModel;
	private SimplePanel dayEditionPanel;

	private Button dayCreateButton;
	private Button dayUpdateButton;
	private Button dayDeleteButton;
	
	private TextBox day;
	private DateBox dayBegin;
	private DateBox dayEnd;
	private TextBox needed;
	
	private Map<Long, CompetitionDayUi> createDays;
	private Map<Long, CompetitionDayUi> updateDays;
	private Set<Long> deleteDays;

	public CompetitionViewImpl(List<CompetitionUi> pData) {
		data = pData;
		dayData = new ArrayList<CompetitionDayUi>(0);
		panel = new HorizontalPanel();
		initWidget(panel);
		
		cellList = new CellList<CompetitionUi>(new CompetitionCell(), new CellListStyle());
		cellList.setRowData(data);
		panel.add(cellList);
		selectionModel = new SingleSelectionModel<CompetitionUi>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			public void onSelectionChange(SelectionChangeEvent pEvent) {
				buildEditionPanel(selectionModel.getSelectedObject());
				
			}
		});
		editionPanel = new SimplePanel();
		editionPanel.setStyleName(CSS.userSpaceContentEdition());
		
		competitionPanel = new VerticalPanel();
		panel.add(competitionPanel);
		competitionPanel.add(editionPanel);
		
		createButton = new Button("");
		createButton.setStyleName(CSS.addButton());
		updateButton = new Button("");
		updateButton.setStyleName(CSS.editButton());
		deleteButton = new Button("");
		deleteButton.setStyleName(CSS.deleteButton());
		updateButton.setVisible(false);
		deleteButton.setVisible(false);
		
		buildCreationPanel();
		
		createDays = new HashMap<Long, CompetitionDayUi>();
		updateDays = new HashMap<Long, CompetitionDayUi>();
		deleteDays = new HashSet<Long>();
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
		updateButton.setVisible(true);
		deleteButton.setVisible(true);
		
		
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
		
		HorizontalPanel lButtonBar = new HorizontalPanel();
		lButtonBar.setStyleName(CSS.buttonBar());
		lButtonBar.add(updateButton);
		lButtonBar.add(deleteButton);
		lButtonBar.add(createButton);
		lPanel.setWidget(5, 0, lButtonBar);
		lCellFormatter.setColSpan(5, 0, 3);
		lCellFormatter.setHorizontalAlignment(5, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		editionPanel.clear();
		editionPanel.setWidget(lPanel);
	}
	
	private void createDayPanel() {
		HorizontalPanel lPanel = new HorizontalPanel();
		
		dayCellList = new CellList<CompetitionDayUi>(new CompetitionDayCell(), new CellDayStyle());
		dayCellList.setRowData(dayData);
		lPanel.add(dayCellList);
		daySelectionModel = new SingleSelectionModel<CompetitionDayUi>();
		dayCellList.setSelectionModel(daySelectionModel);
		daySelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			public void onSelectionChange(SelectionChangeEvent pEvent) {
				buildDayEditionPanel(daySelectionModel.getSelectedObject());
				
			}
		});
		dayEditionPanel = new SimplePanel();
		dayEditionPanel.setStyleName(CSS.userSpaceContentEdition());
		lPanel.add(dayEditionPanel);
		
		dayCreateButton = new Button("");
		dayCreateButton.setStyleName(CSS.addButton());
		dayCreateButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				CompetitionDayUi lDayUi = new CompetitionDayUi();
				lDayUi.setDay(Integer.valueOf(day.getValue()));
				lDayUi.setBegin(dayBegin.getValue());
				lDayUi.setEnd(dayEnd.getValue());
				lDayUi.setNeeded(Integer.valueOf(needed.getValue()));
				createDays.put(lDayUi.getId(), lDayUi);
				Window.alert("La réunion a été ajouté à la compétition, vous devez créer ou mettre à jour la compétition pour sauvegarder vos changements.");
			}
		});
		dayUpdateButton = new Button("");
		dayUpdateButton.setStyleName(CSS.editButton());
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
		dayDeleteButton = new Button("");
		dayDeleteButton.setStyleName(CSS.deleteButton());
		dayDeleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				CompetitionDayUi lCurrentDay = daySelectionModel.getSelectedObject();
				deleteDays.add(lCurrentDay.getId());
			}
		});
		dayUpdateButton.setVisible(false);
		dayDeleteButton.setVisible(false);
		
		buildDayCreationPanel();
		
		competitionPanel.add(lPanel);
	}
	
	private void buildDayEditionPanel(CompetitionDayUi pUi) {
		day.setValue(Integer.toString(pUi.getDay()));
		dayBegin.setValue(pUi.getBegin());
		dayEnd.setValue(pUi.getEnd());
		needed.setValue(Integer.toString(pUi.getNeeded()));
		
		dayUpdateButton.setVisible(true);
		dayDeleteButton.setVisible(true);
	}
	
	private void buildDayCreationPanel() {
		FlexTable lPanel = new FlexTable();
		lPanel.setCellSpacing(6);
		int index = 0;
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lPanel.setHTML(index, 0, "Information sur la réunion");
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		
		index++;
		//Input
		//Day #
		day = new TextBox();
		lPanel.setHTML(index, 0, "Numéro de la réunion");
		lPanel.setWidget(index, 1, day);
		index++;
		
		//Date Begin / End
		dayBegin = new DateBox();
		lPanel.setHTML(index, 0, "Date début");
		lPanel.setWidget(index, 1, dayBegin);
		index++;
		dayEnd = new DateBox();
		lPanel.setHTML(index, 0, "Date fin");
		lPanel.setWidget(index, 1, dayEnd);
		index++;
		
		//Needed
		needed = new TextBox();
		lPanel.setHTML(index, 0, "# officiels nécessaires");
		lPanel.setWidget(index, 1, needed);
		index++;
		
		HorizontalPanel lButtonBar = new HorizontalPanel();
		lButtonBar.setStyleName(CSS.buttonBar());
		lButtonBar.add(dayUpdateButton);
		lButtonBar.add(dayDeleteButton);
		lButtonBar.add(dayCreateButton);
		lPanel.setWidget(index, 0, lButtonBar);
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		
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
	
	public class CellDayStyle implements CellList.Resources {

		public ImageResource cellListSelectedBackground() {
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
					return CSS.userSpaceContentCompetitionDayList();
				}

				public String cellListEvenItem() {
					return "";
				}
			};
		}
		
	}

	public HasClickHandlers getDeleteButton() {
		return deleteButton;
	}

	public Set<Long> getDeleteDays() {
		return deleteDays;
	}
}