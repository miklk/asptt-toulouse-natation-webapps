package com.asptttoulousenatation.client.userspace.admin.swimmer;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.core.client.ui.PopupManager;
import com.asptttoulousenatation.core.client.ui.SwimmerStatWidget;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatDataUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.view.client.ListDataProvider;
import com.ibm.icu.util.Calendar;

public class SwimmerStatViewImpl extends Composite implements SwimmerStatView {

	private VerticalPanel panel;

	private CellTable<SwimmerStatUi> cellTable;

	private List<SwimmerStatWidget> data;
	private Date currentDay;
	
	private Label currentDayLabel;
	
	private DatePicker datePicker;
	
	private Button previousButton;
	private Button nextButton;
	private Button validButton;

	private HorizontalPanel dayPanel;

	public SwimmerStatViewImpl(List<SwimmerStatUi> pSwimmerStats) {
		panel = new VerticalPanel();
		initWidget(panel);
		
		buildDayPanel();

		cellTable = new CellTable<SwimmerStatUi>();
		TextColumn<SwimmerStatUi> swimmerColumn = new TextColumn<SwimmerStatUi>() {
			@Override
			public String getValue(SwimmerStatUi pObject) {
				return pObject.getSwimmer();
			}
		};
		cellTable.addColumn(swimmerColumn, "Nageur(euse)");

		Column<SwimmerStatUi, String> morningColumn = new Column<SwimmerStatUi, String>(
				new TextInputCell()) {

			@Override
			public String getValue(SwimmerStatUi pObject) {
				SwimmerStatDataUi lData = ((SwimmerStatUi) pObject).getMorning();
				final String lValue;
				if (lData != null) {
					lValue = Integer.toString(lData.getDistance());
				} else {
					lValue = "0";
				}
				return lValue;
			}
		};
		cellTable.addColumn(morningColumn, "Matin");
		morningColumn.setFieldUpdater(new FieldUpdater<SwimmerStatUi, String>() {
			
			public void update(int pIndex, SwimmerStatUi pObject, String pValue) {
				SwimmerStatWidget lWidget = data.get(pIndex);
				lWidget.getMorning().setValue(pValue);
				lWidget.setUpdatedMorning(true);
				
			}
		});

		Column<SwimmerStatUi, String> middayColumn = new Column<SwimmerStatUi, String>(
				new TextInputCell()) {

			@Override
			public String getValue(SwimmerStatUi pObject) {
				SwimmerStatDataUi lData = ((SwimmerStatUi) pObject).getMidday();
				final String lValue;
				if (lData != null) {
					lValue = Integer.toString(lData.getDistance());
				} else {
					lValue = "0";
				}
				return lValue;
			}
		};
		cellTable.addColumn(middayColumn, "Midi");
		middayColumn.setFieldUpdater(new FieldUpdater<SwimmerStatUi, String>() {
			
			public void update(int pIndex, SwimmerStatUi pObject, String pValue) {
				SwimmerStatWidget lWidget = data.get(pIndex);
				lWidget.getMidday().setValue(pValue);
				lWidget.setUpdatedMidday(true);
			}
		});

		Column<SwimmerStatUi, String> nightColumn = new Column<SwimmerStatUi, String>(
				new TextInputCell()) {

			@Override
			public String getValue(SwimmerStatUi pObject) {
				SwimmerStatDataUi lData = ((SwimmerStatUi) pObject).getNight();
				final String lValue;
				if (lData != null) {
					lValue = Integer.toString(lData.getDistance());
				} else {
					lValue = "0";
				}
				return lValue;
			}
		};
		cellTable.addColumn(nightColumn, "Soir");
		nightColumn.setFieldUpdater(new FieldUpdater<SwimmerStatUi, String>() {
			
			public void update(int pIndex, SwimmerStatUi pObject, String pValue) {
				SwimmerStatWidget lWidget = data.get(pIndex);
				lWidget.getNight().setValue(pValue);
				lWidget.setUpdatedNight(true);
			}
		});

		Column<SwimmerStatUi, Boolean> bodybuildingColumn = new Column<SwimmerStatUi, Boolean>(
				new CheckboxCell()) {

			@Override
			public Boolean getValue(SwimmerStatUi pObject) {
				SwimmerStatDataUi lData = ((SwimmerStatUi) pObject).getBodybuilding();
				final Boolean lValue;
				if (lData != null) {
					switch (lData.getDistance()) {
					case 0:
						lValue = false;
						break;
					case 1:
						lValue = true;
						break;
					default:
						lValue = false;
					}
				} else {
					lValue = false;
				}
				return lValue;
			}
		};
		cellTable.addColumn(bodybuildingColumn, "Musculation");
		bodybuildingColumn.setFieldUpdater(new FieldUpdater<SwimmerStatUi, Boolean>() {
			
			public void update(int pIndex, SwimmerStatUi pObject, Boolean pValue) {
				SwimmerStatWidget lWidget = data.get(pIndex);
				if(pValue) {
					lWidget.getBodybuilding().setValue("1");
				} else {
					lWidget.getBodybuilding().setValue("0");
				}
				lWidget.setUpdatedBodybuilding(true);
			}
		});

		Column<SwimmerStatUi, String> commentColumn = new Column<SwimmerStatUi, String>(
				new TextInputCell()) {

			@Override
			public String getValue(SwimmerStatUi pObject) {
				String lData = ((SwimmerStatUi) pObject).getComment();
				final String lValue;
				if (lData != null) {
					lValue = lData;
				} else {
					lValue = "";
				}
				return lValue;
			}
		};
		cellTable.addColumn(commentColumn, "Commentaire");
		commentColumn.setFieldUpdater(new FieldUpdater<SwimmerStatUi, String>() {
			
			public void update(int pIndex, SwimmerStatUi pObject, String pValue) {
				SwimmerStatWidget lWidget = data.get(pIndex);
				lWidget.getComment().setValue(pValue);
				lWidget.setUpdatedComment(true);
			}
		});

		setData(pSwimmerStats);
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setPageSize(10);
		pager.setDisplay(cellTable);
		panel.add(pager);
		panel.add(cellTable);
		
//		panel.add(validButton);

	}

	private void buildData(List<SwimmerStatUi> pSwimmerStats) {
		data = new ArrayList<SwimmerStatWidget>(pSwimmerStats.size());
		for (SwimmerStatUi lSwimmerStat : pSwimmerStats) {
			data.add(new SwimmerStatWidget(lSwimmerStat));
		}
	}

	private void buildDayPanel() {
		HorizontalPanel lInnerDayPanel = new HorizontalPanel();
		previousButton = new Button();
		previousButton.setStyleName(CSS.dayPreviousButton());
		nextButton = new Button();
		nextButton.setStyleName(CSS.dayNextButton());
		
		lInnerDayPanel.add(previousButton);
		currentDayLabel = new Label();
		currentDayLabel.setStyleName(CSS.dayLabel());
		lInnerDayPanel.add(currentDayLabel);
		lInnerDayPanel.add(nextButton);
		Button dayButton = new Button("Calendrier");
		dayButton.setStyleName(CSS.swimmerStatActionsCalendar());
		datePicker = new DatePicker();
		dayButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				final PopupPanel lPopup = PopupManager.getInstance().getPopup(false, true, "Calendrier", datePicker);
				lPopup.show();
				
			}
		});
		dayPanel = new HorizontalPanel();
		dayPanel.setStyleName(CSS.dayPanel());
		dayPanel.add(lInnerDayPanel);
		
		dayPanel.add(dayButton);
		dayPanel.setCellHorizontalAlignment(dayButton, HasHorizontalAlignment.ALIGN_RIGHT);
		
		validButton = new Button("Valider");
		validButton.setStyleName(CSS.swimmerStatActionsValid());
		dayPanel.add(validButton);
		dayPanel.setCellHorizontalAlignment(validButton, HasHorizontalAlignment.ALIGN_RIGHT);
		
		panel.add(dayPanel);
	}

	public List<SwimmerStatWidget> getData() {
		return data;
	}

	public HasClickHandlers getValidButton() {
		return validButton;
	}

	public Date getCurrentDay() {
		return currentDay;
	}

	public HasClickHandlers getPreviousButton() {
		return previousButton;
	}

	public HasClickHandlers getNextButton() {
		return nextButton;
	}

	public void setCurrentDay(Date pCurrentDay) {
		currentDay = pCurrentDay;
	}

	public void setData(List<SwimmerStatUi> pSwimmerStats) {
		ListDataProvider<SwimmerStatUi> lDataProvider = new	ListDataProvider<SwimmerStatUi>(pSwimmerStats);
		lDataProvider.addDataDisplay(cellTable);
		cellTable.setRowCount(pSwimmerStats.size(), true);
		buildData(pSwimmerStats);
	}

	public void setCurrentDayText(String pCurrentDayText) {
		currentDayLabel.setText(pCurrentDayText);
	}

	public HasValueChangeHandlers<Date> getNewDate() {
		return datePicker;
	}
}