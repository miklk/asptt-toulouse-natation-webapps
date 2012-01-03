package com.asptttoulousenatation.client.userspace.calendar;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.asptttoulousenatation.core.shared.competition.CompetitionDayUi;
import com.asptttoulousenatation.core.shared.competition.CompetitionUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CompetitionCalendarViewImpl extends Composite implements
		CompetitionCalendarView {

	private static final int COLUMN = 7;
	private static final int ROW = 6;
	private static final DateTimeFormat dayFormat = DateTimeFormat
			.getFormat("d MMM");
	private static final DateTimeFormat dayMonthFormat = DateTimeFormat
			.getFormat("d MMM");
	private static final DateTimeFormat monthFormat = DateTimeFormat
			.getFormat("MMMM yyyy");
	private static final DateTimeFormat hourFormat = DateTimeFormat
			.getFormat("hh:mm");
	private static final DateTimeFormat dayMonthHourFormat = DateTimeFormat
			.getFormat("d MMM hh:mm");

	private VerticalPanel panel;
	private Grid calendarPanel;
	private List<VerticalPanel> dayPanels;
	private static final String[] DAY_NAME = { "Lun.", "Mar.", "Mer.", "Jeu.",
			"Ven.", "Sam.", "Dim." };

	private Date current;
	private Long currentDay;
	private Map<String, List<CompetitionDayUi>> currentCompetitions;
	private List<CompetitionUi> all;
	private Label title;
	private UserUi user;
	
	private Button addOfficielButton;
	private Button removeOfficielButton;

	public CompetitionCalendarViewImpl(UserUi pUser, List<CompetitionUi> pCompetitions) {
		user = pUser;
		panel = new VerticalPanel();
		initWidget(panel);
		all = new ArrayList<CompetitionUi>(pCompetitions);
		currentCompetitions = new HashMap<String, List<CompetitionDayUi>>();

		buildCompetitions();
		calendarPanel = new Grid(ROW, COLUMN);
		calendarPanel.setCellSpacing(0);
		calendarPanel.addStyleName(CSS.userSpaceCalendar());
		dayPanels = new ArrayList<VerticalPanel>(ROW * COLUMN);
		for (int i = 1; i < ROW; i++) {
			for (int j = 0; j < COLUMN; j++) {
				calendarPanel.getCellFormatter().addStyleName(i, j,
						CSS.userSpaceCalendarDay());
				VerticalPanel lPanel = new VerticalPanel();
				dayPanels.add(lPanel);
				calendarPanel.setWidget(i, j, lPanel);
			}
		}

		current = new Date();
		buildHeader();
		panel.add(calendarPanel);
		buildCalendar();
		addOfficielButton = new Button("Officier cette journée");
		removeOfficielButton = new Button("Ne plus officier cette journée");
	}

	private void buildHeader() {
		HorizontalPanel lPanel = new HorizontalPanel();
		Button lPreviousLabel = new Button("<");
		lPreviousLabel.setStyleName(CSS.userSpaceCalendarNav());
		lPreviousLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				current.setMonth(current.getMonth() - 1);
				buildCalendar();
			}
		});
		lPanel.add(lPreviousLabel);

		Button lForwardPanel = new Button(">");
		lForwardPanel.setStyleName(CSS.userSpaceCalendarNav());
		lForwardPanel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				current.setMonth(current.getMonth() + 1);
				buildCalendar();
			}
		});
		lPanel.add(lForwardPanel);

		title = new Label();
		title.setStyleName(CSS.userSpaceCalendarTitle());
		lPanel.add(title);

		lPanel.setStyleName(CSS.userSpaceCalendarHeader());
		panel.add(lPanel);
		panel.add(new HTMLPanel("<hr noshade/>"));
		for (int i = 0; i < COLUMN; i++) {
			calendarPanel.setHTML(0, i, DAY_NAME[i]);
		}
	}

	private void buildCalendar() {
		title.setText(monthFormat.format(current));
		Date lFirstDay = (Date) current.clone();
		lFirstDay.setDate(1);
		lFirstDay.setHours(0);
		lFirstDay.setMinutes(0);
		lFirstDay.setSeconds(0);
		// Get number of date to display before the first day of the current
		// month
		int lFirstDayIndex = lFirstDay.getDay();
		for (int i = 1; i <= (lFirstDayIndex - 1); i++) {
			int lPreviousDayIndex = lFirstDayIndex - i;
			Date lPreviousDay = (Date) lFirstDay.clone();
			lPreviousDay.setDate(lFirstDay.getDate() - i);
			VerticalPanel lVerticalPanel = (VerticalPanel) calendarPanel
					.getWidget(1, (lPreviousDayIndex - 1));
			lVerticalPanel.clear();
			FlowPanel lPanel = new FlowPanel();
			lPanel.add(buildDay(lPreviousDay));
			if (currentCompetitions.containsKey(createKey(lPreviousDay))) {
				for (CompetitionDayUi lCompetitionDayUi : currentCompetitions
						.get(createKey(lPreviousDay))) {
					lPanel.add(buildCompetitionCalendar(lCompetitionDayUi));
				}
			}
			lVerticalPanel.add(lPanel);
		}
		// Build next date
		int lRowIndex = 1;
		for (int i = (lFirstDayIndex + 1); i < (((ROW - 1) * COLUMN) - (lFirstDay.getDay() - 1)); i++) {
			int lNextDayIndex = lFirstDay.getDay() + i;
			Date lNextDay = (Date) lFirstDay.clone();
			lNextDay.setDate(lFirstDay.getDate() + i);
			VerticalPanel lVerticalPanel = (VerticalPanel) calendarPanel
					.getWidget(lRowIndex, getColumn(lNextDayIndex));
			lVerticalPanel.clear();
			FlowPanel lPanel = new FlowPanel();
			lPanel.add(buildDay(lNextDay));
			if (currentCompetitions.containsKey(createKey(lNextDay))) {
				for (CompetitionDayUi lCompetitionDayUi : currentCompetitions
						.get(createKey(lNextDay))) {
					lPanel.add(buildCompetitionCalendar(lCompetitionDayUi));
				}
			}
			lVerticalPanel.add(lPanel);
			if (lNextDay.getDay() == 0) {
				lRowIndex++;
			}

		}
	}

	private void buildCompetitions() {
		for (CompetitionUi lCompetitionUi : all) {
			for (CompetitionDayUi lDayUi : lCompetitionUi.getDays()) {
				for (Date lEntry : lDayUi.getCalendarEntries()) {
					List<CompetitionDayUi> lCompetitions;
					if (currentCompetitions.containsKey(createKey(lEntry))) {
						lCompetitions = currentCompetitions
								.get(createKey(lEntry));
					} else {
						lCompetitions = new ArrayList<CompetitionDayUi>();
					}
					lCompetitions.add(lDayUi);
					currentCompetitions.put(createKey(lEntry), lCompetitions);
				}
			}
		}
	}

	private String createKey(Date pDate) {
		return "" + pDate.getDate() + pDate.getMonth();
	}

	private Widget buildDay(Date pDate) {
		Label lLabel = new Label(dayFormat.format(pDate));
		lLabel.setStyleName(CSS.userSpaceCalendarDayLabel());
		return lLabel;
	}

	private Widget buildCompetitionCalendar(
			final CompetitionDayUi pCompetitionDayUi) {
		final Label lLabel = new Label(hourFormat.format(pCompetitionDayUi
				.getBegin())
				+ " "
				+ pCompetitionDayUi.getCompetitionUi().getTitle());
		lLabel.setStyleName(CSS.userSpaceCalendarCompetitionLabel());
		lLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				openCompetitionDetail(lLabel, pCompetitionDayUi);
			}
		});
		return lLabel;
	}

	private void openCompetitionDetail(Label pLabel,
			CompetitionDayUi pCompetitionDayUi) {
		currentDay = pCompetitionDayUi.getId();
		DecoratedPopupPanel lPopup = new DecoratedPopupPanel(true, true);
		VerticalPanel lPanel = new VerticalPanel();
		Label lTitle = new Label(pCompetitionDayUi.getCompetitionUi()
				.getTitle());
		lTitle.setStyleName(CSS.userSpaceCalendarCompetitionDayValue());
		lPanel.add(lTitle);
		Grid lInfo = new Grid(3, 2);
		// Date
		Label lDateLabel = new Label("Date");
		Label lDateLabelValue = new Label(
				dayMonthHourFormat.format(pCompetitionDayUi.getBegin()) + " - " + hourFormat.format(pCompetitionDayUi.getEnd()));
		lDateLabelValue
				.setStyleName(CSS.userSpaceCalendarCompetitionDayValue());
		lInfo.setWidget(0, 0, lDateLabel);
		lInfo.setWidget(0, 1, lDateLabelValue);

		// Lieu
		Label lLieuLabel = new Label("Lieu");
		Label lLieuLabelValue = new Label(pCompetitionDayUi.getCompetitionUi()
				.getPlace());
		lLieuLabelValue
				.setStyleName(CSS.userSpaceCalendarCompetitionDayValue());
		lInfo.setWidget(1, 0, lLieuLabel);
		lInfo.setWidget(1, 1, lLieuLabelValue);

		// Officiels
		Label lOfficielsLabel = new Label("Officiels inscrits (besoin de " + pCompetitionDayUi.getNeeded() + ")");
		Label lOfficielsLabelValue = new Label();
		lOfficielsLabelValue.setStyleName(CSS
				.userSpaceCalendarCompetitionDayValue());
		lInfo.setWidget(2, 0, lOfficielsLabel);
		String lOfficielLabel = new String();
		Iterator<UserUi> lUserIt = pCompetitionDayUi.getOfficiels().iterator();
		while (lUserIt.hasNext()) {
			UserUi lOfficiel = lUserIt.next();
			lOfficielLabel = lOfficielLabel
					+ lOfficiel.getUserData().getFirstName() + " "
					+ lOfficiel.getUserData().getLastName();
			if (lUserIt.hasNext()) {
				lOfficielLabel = lOfficielLabel + ",";
			}
		}
		if (lOfficielLabel.isEmpty()) {
			lOfficielsLabelValue.setText("aucun");
		} else {
			lOfficielsLabelValue.setText(lOfficielLabel);
		}
		lInfo.setWidget(2, 1, lOfficielsLabelValue);
		lPanel.add(lInfo);
		if(pCompetitionDayUi.isOfficiel(user)) {
			lPanel.remove(addOfficielButton);
			lPanel.add(removeOfficielButton);
			lPanel.setCellHorizontalAlignment(removeOfficielButton, HasHorizontalAlignment.ALIGN_CENTER);
		}
		else {
			lPanel.remove(removeOfficielButton);
			lPanel.add(addOfficielButton);
			lPanel.setCellHorizontalAlignment(addOfficielButton, HasHorizontalAlignment.ALIGN_CENTER);
		}
		addOfficielButton.setStyleName(CSS
				.userSpaceCalendarCompetitionDayButton());
		removeOfficielButton.setStyleName(CSS
				.userSpaceCalendarCompetitionDayButton());

		lPopup.add(lPanel);
		lPopup.showRelativeTo(pLabel);
	}

	public HasClickHandlers addOfficielButton() {
		return addOfficielButton;
	}

	public HasClickHandlers removeOfficielButton() {
		return removeOfficielButton;
	}

	public Long getCompetitionDayId() {
		return currentDay;
	}
	
	private int getColumn(int dayIndex) {
		final int result;
		switch(dayIndex) {
		case 0: result = dayIndex;
		break;
		default: result = (dayIndex - 1) % 7;
		}
		return result;
	}
}