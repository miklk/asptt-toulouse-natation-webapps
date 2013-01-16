package com.asptttoulousenatation.client.userspace.calendar;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.competition.GetAllCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.GetAllCompetitionResult;
import com.asptttoulousenatation.core.shared.competition.OfficielDayAction;
import com.asptttoulousenatation.core.shared.competition.OfficielDayResult;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public class CompetitionCalendarActivity extends
		MyAbstractActivity<CompetitionCalendarPlace> {

	private UserUi user;
	
	public CompetitionCalendarActivity(CompetitionCalendarPlace pPlace,
			ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		final SimplePanel lPanel = new SimplePanel();
		dispatchAsync.execute(new GetAllCompetitionAction(), new AsyncCallback<GetAllCompetitionResult>() {

			public void onFailure(Throwable pCaught) {
				Window.alert("Erreur " + pCaught);
			}

			public void onSuccess(GetAllCompetitionResult pResult) {
				final CompetitionCalendarView lView = clientFactory.getCompetitionCalendarView(user, pResult.getCompetitions());
				lView.addOfficielButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						dispatchAsync.execute(new OfficielDayAction(user.getId(), lView.getCompetitionId(), lView.getCompetitionDayId(), true), new AsyncCallback<OfficielDayResult>() {

							public void onFailure(Throwable pCaught) {
								Window.alert("Erreur " + pCaught.getMessage());
							}

							public void onSuccess(OfficielDayResult pResult) {
								lView.switchOfficielButton(pResult.getCompetitionDayUi());
							}
						});
					}
				});
				lView.removeOfficielButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						dispatchAsync.execute(new OfficielDayAction(user.getId(), lView.getCompetitionId(), lView.getCompetitionDayId(), false), new AsyncCallback<OfficielDayResult>() {

							public void onFailure(Throwable pCaught) {
								Window.alert("Erreur " + pCaught.getMessage());
							}

							public void onSuccess(OfficielDayResult pResult) {
								lView.switchOfficielButton(pResult.getCompetitionDayUi());
							}
						});
					}
				});
				lPanel.setWidget(lView);
			}
		});
		
		pPanel.setWidget(lPanel);
	}

	public UserUi getUser() {
		return user;
	}

	public void setUser(UserUi pUser) {
		user = pUser;
	}
}