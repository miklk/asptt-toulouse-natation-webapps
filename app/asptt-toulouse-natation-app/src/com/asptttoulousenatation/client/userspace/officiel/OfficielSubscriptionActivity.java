package com.asptttoulousenatation.client.userspace.officiel;

import java.util.Date;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.competition.GetAllCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.GetAllCompetitionResult;
import com.asptttoulousenatation.core.shared.competition.OfficielDayAction;
import com.asptttoulousenatation.core.shared.competition.OfficielDayResult;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

public class OfficielSubscriptionActivity extends MyAbstractActivity<OfficielSubscriptionPlace> {

	private UserUi user;
	
	public OfficielSubscriptionActivity(OfficielSubscriptionPlace pPlace,
			ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	@Override
	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		final SimplePanel lPanel = new SimplePanel();
		final OfficielSubscriptionView lView = clientFactory.getOfficielSubscriptionView();
		lPanel.setWidget(lView);
		dispatchAsync.execute(new GetAllCompetitionAction(), new AsyncCallback<GetAllCompetitionResult>() {

			public void onFailure(Throwable pCaught) {
				Window.alert("Erreur " + pCaught.getMessage());
			}

			public void onSuccess(GetAllCompetitionResult pResult) {
				lView.setData(getUser(), pResult.getCompetitions());
				lView.setSubscriptionCommand(new Command() {
					public void execute() {
						dispatchAsync.execute(new OfficielDayAction(user.getId(), lView.getCompetitionId(), lView.getCompetitionDayId(), true), new AsyncCallback<OfficielDayResult>() {

							public void onFailure(Throwable pCaught) {
								Window.alert("Erreur " + pCaught.getMessage());
							}

							public void onSuccess(OfficielDayResult pResult) {
								lView.setData(getUser(), pResult.getCompetitions());
							}
						});
					}
				});
				lView.setUnsubscriptionCommand(new Command() {
					public void execute() {
						dispatchAsync.execute(new OfficielDayAction(user.getId(), lView.getCompetitionId(), lView.getCompetitionDayId(), false), new AsyncCallback<OfficielDayResult>() {

							public void onFailure(Throwable pCaught) {
								Window.alert("Erreur " + pCaught.getMessage());
							}

							public void onSuccess(OfficielDayResult pResult) {
								lView.setData(getUser(), pResult.getCompetitions());
							}
						});
					}
				});
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