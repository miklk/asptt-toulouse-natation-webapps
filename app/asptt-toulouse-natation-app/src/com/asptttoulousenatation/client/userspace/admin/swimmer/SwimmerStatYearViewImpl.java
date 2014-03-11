package com.asptttoulousenatation.client.userspace.admin.swimmer;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatYearUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SwimmerStatYearViewImpl extends Composite implements
		SwimmerStatYearView {

	private VerticalPanel panel;

	private CellTable<SwimmerStatYearUi> cellTable;

	private HorizontalPanel dayPanel;

	private Date currentDay;

	private Label currentDayLabel;

	private Button previousButton;
	private Button nextButton;

	public SwimmerStatYearViewImpl(List<SwimmerStatYearUi> pSwimmerStats) {
		panel = new VerticalPanel();
		initWidget(panel);

		buildDayPanel();

		cellTable = new CellTable<SwimmerStatYearUi>();
		TextColumn<SwimmerStatYearUi> swimmerColumn = new TextColumn<SwimmerStatYearUi>() {
			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return pObject.getSwimmer();
			}
		};
		cellTable.addColumn(swimmerColumn, "Nageur(euse)");

		
		TextColumn<SwimmerStatYearUi> distanceColumn9 = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getDistances().get(8));
			}
		};
		cellTable.addColumn(distanceColumn9, "Septembre");
		TextColumn<SwimmerStatYearUi> distanceColumn10 = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getDistances().get(9));
			}
		};
		cellTable.addColumn(distanceColumn10, "Octobre");
		TextColumn<SwimmerStatYearUi> distanceColumn11 = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getDistances().get(10));
			}
		};
		cellTable.addColumn(distanceColumn11, "Novembre");
		TextColumn<SwimmerStatYearUi> distanceColumn12 = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getDistances().get(11));
			}
		};
		cellTable.addColumn(distanceColumn12, "Décembre");
		TextColumn<SwimmerStatYearUi> distanceColumn1 = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getDistances().get(0));
			}
		};
		cellTable.addColumn(distanceColumn1, "Janvier");
		TextColumn<SwimmerStatYearUi> distanceColumn2 = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getDistances().get(1));
			}
		};
		cellTable.addColumn(distanceColumn2, "Février");
		TextColumn<SwimmerStatYearUi> distanceColumn3 = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getDistances().get(2));
			}
		};
		cellTable.addColumn(distanceColumn3, "Mars");
		TextColumn<SwimmerStatYearUi> distanceColumn4 = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getDistances().get(3));
			}
		};
		cellTable.addColumn(distanceColumn4, "Avril");
		TextColumn<SwimmerStatYearUi> distanceColumn5 = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getDistances().get(4));
			}
		};
		cellTable.addColumn(distanceColumn5, "Mai");
		TextColumn<SwimmerStatYearUi> distanceColumn6 = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getDistances().get(5));
			}
		};
		cellTable.addColumn(distanceColumn6, "Juin");
		TextColumn<SwimmerStatYearUi> distanceColumn7 = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getDistances().get(6));
			}
		};
		cellTable.addColumn(distanceColumn7, "Juillet");
		TextColumn<SwimmerStatYearUi> distanceColumn8 = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getDistances().get(7));
			}
		};
		cellTable.addColumn(distanceColumn8, "Août");

		TextColumn<SwimmerStatYearUi> totalColumn = new TextColumn<SwimmerStatYearUi>() {

			@Override
			public String getValue(SwimmerStatYearUi pObject) {
				return Integer.toString(pObject.getTotal());
			}
		};
		cellTable.addColumn(totalColumn, "Total");

		setData(pSwimmerStats);
		panel.add(cellTable);
	}

	private void buildDayPanel() {
		HorizontalPanel lInnerDayPanel = new HorizontalPanel();
		previousButton = new Button();
		previousButton.setStyleName(CSS.dayPreviousButton());
		nextButton = new Button();
		nextButton.setStyleName(CSS.dayNextButton());

		currentDayLabel = new Label();
		currentDayLabel.setStyleName(CSS.dayLabel());

		lInnerDayPanel.add(previousButton);
		lInnerDayPanel.add(currentDayLabel);
		lInnerDayPanel.add(nextButton);

		dayPanel = new HorizontalPanel();
		dayPanel.setStyleName(CSS.dayPanel());
		dayPanel.add(lInnerDayPanel);
		panel.add(dayPanel);
	}

	public Date getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(Date pCurrentDay) {
		currentDay = pCurrentDay;
	}

	public HasClickHandlers getPreviousButton() {
		return previousButton;
	}

	public HasClickHandlers getNextButton() {
		return nextButton;
	}

	public void setData(List<SwimmerStatYearUi> pSwimmerStats) {
		cellTable.setRowCount(pSwimmerStats.size(), true);
		cellTable.setRowData(pSwimmerStats);
	}

	public void setCurrentDayText(String pCurrentDayText) {
		currentDayLabel.setText(pCurrentDayText);
	}
}