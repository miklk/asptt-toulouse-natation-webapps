package com.asptttoulousenatation.client.userspace.admin.swimmer;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatMonthUi;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SwimmerStatMonthViewImpl extends Composite implements
		SwimmerStatMonthView {

	private VerticalPanel panel;

	private CellTable<SwimmerStatMonthUi> cellTable;

	private HorizontalPanel dayPanel;

	private Date currentDay;

	private Label currentDayLabel;

	private Button previousButton;
	private Button nextButton;

	public SwimmerStatMonthViewImpl(List<SwimmerStatMonthUi> pSwimmerStats) {
		panel = new VerticalPanel();
		initWidget(panel);

		buildDayPanel();

		cellTable = new CellTable<SwimmerStatMonthUi>();
		TextColumn<SwimmerStatMonthUi> swimmerColumn = new TextColumn<SwimmerStatMonthUi>() {
			@Override
			public String getValue(SwimmerStatMonthUi pObject) {
				return pObject.getSwimmer();
			}
		};
		cellTable.addColumn(swimmerColumn, "Nageur(euse)");

		TextColumn<SwimmerStatMonthUi> distanceColumn1 = new TextColumn<SwimmerStatMonthUi>() {

			@Override
			public String getValue(SwimmerStatMonthUi pObject) {
				return Integer.toString(pObject.getDistances().get(0));
			}
		};
		cellTable.addColumn(distanceColumn1, "Semaine 1");
		TextColumn<SwimmerStatMonthUi> distanceColumn2 = new TextColumn<SwimmerStatMonthUi>() {

			@Override
			public String getValue(SwimmerStatMonthUi pObject) {
				return Integer.toString(pObject.getDistances().get(1));
			}
		};
		cellTable.addColumn(distanceColumn2, "Semaine 2");
		TextColumn<SwimmerStatMonthUi> distanceColumn3 = new TextColumn<SwimmerStatMonthUi>() {

			@Override
			public String getValue(SwimmerStatMonthUi pObject) {
				return Integer.toString(pObject.getDistances().get(2));
			}
		};
		cellTable.addColumn(distanceColumn3, "Semaine 3");
		TextColumn<SwimmerStatMonthUi> distanceColumn4 = new TextColumn<SwimmerStatMonthUi>() {

			@Override
			public String getValue(SwimmerStatMonthUi pObject) {
				return Integer.toString(pObject.getDistances().get(3));
			}
		};
		cellTable.addColumn(distanceColumn4, "Semaine 4");

		TextColumn<SwimmerStatMonthUi> totalColumn = new TextColumn<SwimmerStatMonthUi>() {

			@Override
			public String getValue(SwimmerStatMonthUi pObject) {
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

	public void setData(List<SwimmerStatMonthUi> pSwimmerStats) {
		cellTable.setRowCount(pSwimmerStats.size(), true);
		cellTable.setRowData(pSwimmerStats);
	}

	public void setCurrentDayText(String pCurrentDayText) {
		currentDayLabel.setText(pCurrentDayText);
	}
}