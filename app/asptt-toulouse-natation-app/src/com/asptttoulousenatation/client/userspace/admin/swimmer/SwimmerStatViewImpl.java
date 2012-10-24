package com.asptttoulousenatation.client.userspace.admin.swimmer;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.core.client.ui.SwimmerStatWidget;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatDataUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SwimmerStatViewImpl extends Composite implements SwimmerStatView {

	private VerticalPanel panel;

	private CellTable<SwimmerStatUi> cellTable;

	private List<SwimmerStatWidget> data;
	private Date currentDay;
	
	private Label currentDayLabel;

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
				List<SwimmerStatDataUi> lData = ((SwimmerStatUi) pObject)
						.getData();
				final String lValue;
				if (lData != null && lData.size() >= 1) {
					lValue = Integer.toString(lData.get(0).getDistance());
				} else {
					lValue = "0";
				}
				return lValue;
			}
		};
		cellTable.addColumn(morningColumn, "Matin");
		morningColumn.setFieldUpdater(new FieldUpdater<SwimmerStatUi, String>() {
			
			public void update(int pIndex, SwimmerStatUi pObject, String pValue) {
				data.get(pIndex).getMorning().setValue(pValue);
			}
		});

		Column<SwimmerStatUi, String> middayColumn = new Column<SwimmerStatUi, String>(
				new TextInputCell()) {

			@Override
			public String getValue(SwimmerStatUi pObject) {
				List<SwimmerStatDataUi> lData = ((SwimmerStatUi) pObject)
						.getData();
				final String lValue;
				if (lData != null && lData.size() >= 2) {
					lValue = Integer.toString(lData.get(1).getDistance());
				} else {
					lValue = "0";
				}
				return lValue;
			}
		};
		cellTable.addColumn(middayColumn, "Midi");
		middayColumn.setFieldUpdater(new FieldUpdater<SwimmerStatUi, String>() {
			
			public void update(int pIndex, SwimmerStatUi pObject, String pValue) {
				data.get(pIndex).getMidday().setValue(pValue);
			}
		});

		Column<SwimmerStatUi, String> nightColumn = new Column<SwimmerStatUi, String>(
				new TextInputCell()) {

			@Override
			public String getValue(SwimmerStatUi pObject) {
				List<SwimmerStatDataUi> lData = ((SwimmerStatUi) pObject)
						.getData();
				final String lValue;
				if (lData != null && lData.size() >= 3) {
					lValue = Integer.toString(lData.get(2).getDistance());
				} else {
					lValue = "0";
				}
				return lValue;
			}
		};
		cellTable.addColumn(nightColumn, "Soir");
		nightColumn.setFieldUpdater(new FieldUpdater<SwimmerStatUi, String>() {
			
			public void update(int pIndex, SwimmerStatUi pObject, String pValue) {
				data.get(pIndex).getNight().setValue(pValue);
			}
		});

		Column<SwimmerStatUi, Boolean> bodybuildingColumn = new Column<SwimmerStatUi, Boolean>(
				new CheckboxCell()) {

			@Override
			public Boolean getValue(SwimmerStatUi pObject) {
				List<SwimmerStatDataUi> lData = ((SwimmerStatUi) pObject)
						.getData();
				final Boolean lValue;
				if (lData != null && lData.size() >= 4) {
					switch (lData.get(3).getDistance()) {
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
				if(pValue) {
					data.get(pIndex).getBodybuilding().setValue("1");
				} else {
					data.get(pIndex).getBodybuilding().setValue("0");
				}
			}
		});

		Column<SwimmerStatUi, String> commentColumn = new Column<SwimmerStatUi, String>(
				new TextInputCell()) {

			@Override
			public String getValue(SwimmerStatUi pObject) {
				List<SwimmerStatDataUi> lData = ((SwimmerStatUi) pObject)
						.getData();
				final String lValue;
				if (lData != null && lData.size() >= 1) {
					lValue = lData.get(0).getComment();
				} else {
					lValue = "";
				}
				return lValue;
			}
		};
		cellTable.addColumn(commentColumn, "Commentaire");
		commentColumn.setFieldUpdater(new FieldUpdater<SwimmerStatUi, String>() {
			
			public void update(int pIndex, SwimmerStatUi pObject, String pValue) {
				data.get(pIndex).getComment().setValue(pValue);
			}
		});

		setData(pSwimmerStats);
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
		dayPanel = new HorizontalPanel();
		dayPanel.setStyleName(CSS.dayPanel());
		dayPanel.add(lInnerDayPanel);
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
		cellTable.setRowCount(pSwimmerStats.size(), true);
		cellTable.setRowData(pSwimmerStats);
		buildData(pSwimmerStats);
	}

	public void setCurrentDayText(String pCurrentDayText) {
		currentDayLabel.setText(pCurrentDayText);
	}
}