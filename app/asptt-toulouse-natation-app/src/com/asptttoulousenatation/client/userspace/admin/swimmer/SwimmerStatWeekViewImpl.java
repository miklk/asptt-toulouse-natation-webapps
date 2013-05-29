package com.asptttoulousenatation.client.userspace.admin.swimmer;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatWeekUi;
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

public class SwimmerStatWeekViewImpl extends Composite implements
		SwimmerStatWeekView {

	private VerticalPanel panel;

	private CellTable<SwimmerStatWeekUi> cellTable;

	private HorizontalPanel dayPanel;

	private Date currentDay;

	private Label currentDayLabel;

	private Button previousButton;
	private Button nextButton;

	public SwimmerStatWeekViewImpl(List<SwimmerStatWeekUi> pSwimmerStats) {
		panel = new VerticalPanel();
		initWidget(panel);

		buildDayPanel();

		cellTable = new CellTable<SwimmerStatWeekUi>();
		TextColumn<SwimmerStatWeekUi> swimmerColumn = new TextColumn<SwimmerStatWeekUi>() {
			@Override
			public String getValue(SwimmerStatWeekUi pObject) {
				return pObject.getSwimmer();
			}
		};
		cellTable.addColumn(swimmerColumn, "Nageur(euse)");

		Column<SwimmerStatWeekUi, SafeHtml> distanceColumn1 = new Column<SwimmerStatWeekUi, SafeHtml>(
				new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(SwimmerStatWeekUi pObject) {
				Integer[] distances = pObject.getDistances().get(0);
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<table><tr><td style='border-left: 1px solid #7faed0'>Matin</td><td style='border-left: 1px solid #7faed0'>Midi</td><td style='border-left: 1px solid #7faed0'>Soir</td></tr>");
				sb.appendHtmlConstant("<tr><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[0]));
				sb.appendHtmlConstant("</td><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[1]));
				sb.appendHtmlConstant("</td><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[2]));
				sb.appendHtmlConstant("</td></tr></table>");
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(distanceColumn1, "Lundi");
		Column<SwimmerStatWeekUi, SafeHtml> distanceColumn2 = new Column<SwimmerStatWeekUi, SafeHtml>(
				new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(SwimmerStatWeekUi pObject) {
				Integer[] distances = pObject.getDistances().get(1);
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<table><tr><td style='border-left: 1px solid #7faed0'>Matin</td><td style='border-left: 1px solid #7faed0'>Midi</td><td style='border-left: 1px solid #7faed0'>Soir</td></tr>");
				sb.appendHtmlConstant("<tr><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[0]));
				sb.appendHtmlConstant("</td><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[1]));
				sb.appendHtmlConstant("</td><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[2]));
				sb.appendHtmlConstant("</td></tr></table>");
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(distanceColumn2, "Mardi");
		Column<SwimmerStatWeekUi, SafeHtml> distanceColumn3 = new Column<SwimmerStatWeekUi, SafeHtml>(
				new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(SwimmerStatWeekUi pObject) {
				Integer[] distances = pObject.getDistances().get(2);
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<table><tr><td style='border-left: 1px solid #7faed0'>Matin</td><td style='border-left: 1px solid #7faed0'>Midi</td><td style='border-left: 1px solid #7faed0'>Soir</td></tr>");
				sb.appendHtmlConstant("<tr><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[0]));
				sb.appendHtmlConstant("</td><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[1]));
				sb.appendHtmlConstant("</td><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[2]));
				sb.appendHtmlConstant("</td></tr></table>");
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(distanceColumn3, "Mercredi");
		Column<SwimmerStatWeekUi, SafeHtml> distanceColumn4 = new Column<SwimmerStatWeekUi, SafeHtml>(
				new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(SwimmerStatWeekUi pObject) {
				Integer[] distances = pObject.getDistances().get(3);
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<table><tr><td style='border-left: 1px solid #7faed0'>Matin</td><td style='border-left: 1px solid #7faed0'>Midi</td><td style='border-left: 1px solid #7faed0'>Soir</td></tr>");
				sb.appendHtmlConstant("<tr><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[0]));
				sb.appendHtmlConstant("</td><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[1]));
				sb.appendHtmlConstant("</td><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[2]));
				sb.appendHtmlConstant("</td></tr></table>");
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(distanceColumn4, "Jeudi");
		Column<SwimmerStatWeekUi, SafeHtml> distanceColumn5 = new Column<SwimmerStatWeekUi, SafeHtml>(
				new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(SwimmerStatWeekUi pObject) {
				Integer[] distances = pObject.getDistances().get(4);
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<table><tr><td style='border-left: 1px solid #7faed0'>Matin</td><td style='border-left: 1px solid #7faed0'>Midi</td><td style='border-left: 1px solid #7faed0'>Soir</td></tr>");
				sb.appendHtmlConstant("<tr><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[0]));
				sb.appendHtmlConstant("</td><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[1]));
				sb.appendHtmlConstant("</td><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[2]));
				sb.appendHtmlConstant("</td></tr></table>");
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(distanceColumn5, "Vendredi");
		Column<SwimmerStatWeekUi, SafeHtml> distanceColumn6 = new Column<SwimmerStatWeekUi, SafeHtml>(
				new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(SwimmerStatWeekUi pObject) {
				Integer[] distances = pObject.getDistances().get(5);
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<table><tr><td style='border-left: 1px solid #7faed0'>Matin</td><td style='border-left: 1px solid #7faed0'>Midi</td><td style='border-left: 1px solid #7faed0'>Soir</td></tr>");
				sb.appendHtmlConstant("<tr><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[0]));
				sb.appendHtmlConstant("</td><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[1]));
				sb.appendHtmlConstant("</td><td style='border-top: 1px solid #7faed0;border-left: 1px solid #7faed0'>");
				sb.appendEscaped(Integer.toString(distances[2]));
				sb.appendHtmlConstant("</td></tr></table>");
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(distanceColumn6, "Samedi");

		TextColumn<SwimmerStatWeekUi> totalColumn = new TextColumn<SwimmerStatWeekUi>() {

			@Override
			public String getValue(SwimmerStatWeekUi pObject) {
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

	public void setData(List<SwimmerStatWeekUi> pSwimmerStats) {
		cellTable.setRowCount(pSwimmerStats.size(), true);
		cellTable.setRowData(pSwimmerStats);
	}

	public void setCurrentDayText(String pCurrentDayText) {
		currentDayLabel.setText(pCurrentDayText);
	}
}