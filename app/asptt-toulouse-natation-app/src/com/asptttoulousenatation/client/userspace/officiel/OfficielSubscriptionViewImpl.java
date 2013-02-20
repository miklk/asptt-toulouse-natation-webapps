package com.asptttoulousenatation.client.userspace.officiel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.asptttoulousenatation.core.shared.competition.CompetitionDayUi;
import com.asptttoulousenatation.core.shared.competition.CompetitionUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextButtonCell;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OfficielSubscriptionViewImpl extends Composite implements
		OfficielSubscriptionView {

	private static final String SUBSCRIBE = "S'inscrire";

	private static final String UNSUBSCRIBE = "Se désinscrire";

	private Panel panel;

	private CellTable<CompetitionDayUi> cellTable;

	private Button addOfficielButton;
	private Button removeOfficielButton;

	private UserUi currentUser;
	private Command subscriptionCommand;
	private Command unsubscriptionCommand;
	private Long currentCompetition;
	private Long currentCompetitionDay;
	private List<CompetitionDayUi> competitionDays;

	public OfficielSubscriptionViewImpl() {
		panel = new VerticalPanel();
		initWidget(panel);

		cellTable = new CellTable<CompetitionDayUi>();
		TextColumn<CompetitionDayUi> lDateColumn = new TextColumn<CompetitionDayUi>() {

			@Override
			public String getValue(CompetitionDayUi pObject) {
				DateTimeFormat formatter = DateTimeFormat
						.getFormat("dd MMM yyyy HH:mm");
				return formatter.format(pObject.getBegin());
			}
		};
		cellTable.addColumn(lDateColumn, "Date/Heure");

		TextColumn<CompetitionDayUi> lCompetitionColumn = new TextColumn<CompetitionDayUi>() {

			@Override
			public String getValue(CompetitionDayUi pObject) {
				return pObject.getCompetitionUi().getTitle();
			}
		};
		cellTable.addColumn(lCompetitionColumn, "Intitulé");

		TextColumn<CompetitionDayUi> lOfficielsColumn = new TextColumn<CompetitionDayUi>() {

			@Override
			public String getValue(CompetitionDayUi pObject) {
				StringBuilder lOfficiels = new StringBuilder();
				for (UserUi lUser : pObject.getOfficiels()) {
					lOfficiels.append(lUser.getUserData().getLastName())
							.append(" ")
							.append(lUser.getUserData().getFirstName())
							.append("\n");
				}
				return lOfficiels.toString();
			}
		};
		cellTable.addColumn(lOfficielsColumn, "Officiels inscrits");

		Column<CompetitionDayUi, String> lActionColumn = new Column<CompetitionDayUi, String>(
				new TextButtonCell()) {
			@Override
			public String getValue(CompetitionDayUi pObject) {
				boolean found = false;
				Iterator<UserUi> officielIt = pObject.getOfficiels().iterator();
				while (officielIt.hasNext() && !found) {
					UserUi officiel = officielIt.next();
					found = currentUser.getId() == officiel.getId();
				}
				final String buttonText;
				if (found) {
					buttonText = UNSUBSCRIBE;
				} else {
					buttonText = SUBSCRIBE;
				}
				return buttonText;
			}
		};
		cellTable.addColumn(lActionColumn, "Actions");
		lActionColumn.setFieldUpdater(new FieldUpdater<CompetitionDayUi, String>() {
			
			public void update(int pIndex, CompetitionDayUi pObject, String pValue) {
				currentCompetition = pObject.getCompetitionUi().getId();
				currentCompetitionDay = pObject.getId();
				if(SUBSCRIBE.equals(pValue)) {
					subscriptionCommand.execute();
				} else {
					unsubscriptionCommand.execute();
				}
			}
		});
		panel.add(cellTable);
	}

	public void setData(UserUi pUser, List<CompetitionUi> pCompetitions) {
		currentUser = pUser;
		competitionDays = getDays(pCompetitions);
		cellTable.setRowData(competitionDays);
		cellTable.setRowCount(pCompetitions.size(), true);
	}

	private List<CompetitionDayUi> getDays(List<CompetitionUi> pCompetitions) {
		List<CompetitionDayUi> results = new ArrayList<CompetitionDayUi>();
		for (CompetitionUi lCompetition : pCompetitions) {
			results.addAll(lCompetition.getDays());
		}
		return results;
	}

	public HasClickHandlers addOfficielButton() {
		return addOfficielButton;
	}

	public HasClickHandlers removeOfficielButton() {
		return removeOfficielButton;
	}

	public Long getCompetitionId() {
		return currentCompetition;
	}

	public Long getCompetitionDayId() {
		return currentCompetitionDay;
	}

	public void setSubscriptionCommand(Command pSubscriptionCommand) {
		subscriptionCommand = pSubscriptionCommand;
	}

	public void setUnsubscriptionCommand(Command pUnsubscriptionCommand) {
		unsubscriptionCommand = pUnsubscriptionCommand;
	}
	
}