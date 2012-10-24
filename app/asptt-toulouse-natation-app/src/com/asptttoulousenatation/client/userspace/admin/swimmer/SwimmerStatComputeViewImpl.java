package com.asptttoulousenatation.client.userspace.admin.swimmer;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatComputeUi;
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

public class SwimmerStatComputeViewImpl extends Composite implements
		SwimmerStatComputeView {

	private VerticalPanel panel;

	private CellTable<SwimmerStatComputeUi> cellTable;

	private HorizontalPanel dayPanel;

	private Date currentDay;

	private Label currentDayLabel;

	private Button previousButton;
	private Button nextButton;

	public SwimmerStatComputeViewImpl(List<SwimmerStatComputeUi> pSwimmerStats) {
		panel = new VerticalPanel();
		initWidget(panel);

		buildDayPanel();

		cellTable = new CellTable<SwimmerStatComputeUi>();
		TextColumn<SwimmerStatComputeUi> swimmerColumn = new TextColumn<SwimmerStatComputeUi>() {
			@Override
			public String getValue(SwimmerStatComputeUi pObject) {
				return pObject.getSwimmer();
			}
		};
		cellTable.addColumn(swimmerColumn, "Nageur(euse)");

		TextColumn<SwimmerStatComputeUi> distanceColumn = new TextColumn<SwimmerStatComputeUi>() {

			@Override
			public String getValue(SwimmerStatComputeUi pObject) {
				return Integer.toString(pObject.getDistance());
			}
		};
		cellTable.addColumn(distanceColumn, "Kilométrage");
		
		TextColumn<SwimmerStatComputeUi> bodybuildingColumn = new TextColumn<SwimmerStatComputeUi>() {

			@Override
			public String getValue(SwimmerStatComputeUi pObject) {
				return Integer.toString(pObject.getBodybuilding());
			}
		};
		cellTable.addColumn(bodybuildingColumn, "Musculation");
		
		Column<SwimmerStatComputeUi, SafeHtml> commentColumn = new Column<SwimmerStatComputeUi, SafeHtml>(new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(SwimmerStatComputeUi pObject) {
				SafeHtmlBuilder lCommentBuilder = new SafeHtmlBuilder();
				Iterator<String> lCommentIterator = pObject.getComment().listIterator();
				while(lCommentIterator.hasNext()) {
					lCommentBuilder.appendEscaped(lCommentIterator.next());
					if(lCommentIterator.hasNext()) {
						lCommentBuilder.appendHtmlConstant("<br />");
					}
				}
				return lCommentBuilder.toSafeHtml();
			}
		};
		cellTable.addColumn(commentColumn, "Commentaires");
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

	public void setData(List<SwimmerStatComputeUi> pSwimmerStats) {
		cellTable.setRowCount(pSwimmerStats.size(), true);
		cellTable.setRowData(pSwimmerStats);
	}

	public void setCurrentDayText(String pCurrentDayText) {
		currentDayLabel.setText(pCurrentDayText);
	}
}