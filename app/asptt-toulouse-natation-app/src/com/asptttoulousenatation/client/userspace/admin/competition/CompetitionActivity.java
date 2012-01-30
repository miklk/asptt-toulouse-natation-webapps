package com.asptttoulousenatation.client.userspace.admin.competition;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.userspace.admin.event.UpdateContentEvent;
import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.core.client.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.competition.CreateCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.CreateCompetitionResult;
import com.asptttoulousenatation.core.shared.competition.DeleteCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.DeleteCompetitionResult;
import com.asptttoulousenatation.core.shared.competition.GetAllCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.GetAllCompetitionResult;
import com.asptttoulousenatation.core.shared.competition.UpdateCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.UpdateCompetitionResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public class CompetitionActivity extends MyAbstractActivity<CompetitionPlace> {

	public CompetitionActivity(CompetitionPlace pPlace,
			ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, final EventBus pEventBus) {
		final SimplePanel lPanel = new SimplePanel();
		dispatchAsync.execute(new GetAllCompetitionAction(),
				new AsyncCallback<GetAllCompetitionResult>() {

					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur: " + pCaught.getMessage());
					}

					public void onSuccess(GetAllCompetitionResult pResult) {
						final CompetitionView lView = clientFactory
								.getCompetitionView(pResult.getCompetitions());
						lView.getCreateButton().addClickHandler(
								new ClickHandler() {
									public void onClick(ClickEvent pEvent) {
										CreateCompetitionAction lAction = new CreateCompetitionAction(
												lView.getCompetitionSaison()
														.getValue(), lView
														.getCompetitionTitle()
														.getValue(), lView
														.getCompetitionPlace()
														.getValue(), lView
														.getCompetitionBegin()
														.getValue(), lView
														.getCompetitionEnd()
														.getValue(), lView
														.getCreateDays());
										dispatchAsync
												.execute(
														lAction,
														new AsyncCallback<CreateCompetitionResult>() {
															public void onFailure(
																	Throwable pCaught) {
																Window.alert("Erreur: "
																		+ pCaught
																				.getMessage());
															}

															public void onSuccess(
																	CreateCompetitionResult pResult) {
																Window.alert("Créé !");
																pEventBus.fireEvent(new UpdateContentEvent(MenuItems.COMPETITION_EDITION));
															}
														});
									}
								});
						lView.getUpdateButton().addClickHandler(
								new ClickHandler() {
									public void onClick(ClickEvent pEvent) {
										UpdateCompetitionAction lAction = new UpdateCompetitionAction(
												lView.getCompetition(), lView
														.getCompetitionSaison()
														.getValue(), lView
														.getCompetitionTitle()
														.getValue(), lView
														.getCompetitionPlace()
														.getValue(), lView
														.getCompetitionBegin()
														.getValue(), lView
														.getCompetitionEnd()
														.getValue(), lView
														.getUpdateDays(), lView
														.getDeleteDays());
										dispatchAsync
												.execute(
														lAction,
														new AsyncCallback<UpdateCompetitionResult>() {
															public void onFailure(
																	Throwable pCaught) {
																Window.alert("Erreur: "
																		+ pCaught
																				.getMessage());
															}

															public void onSuccess(
																	UpdateCompetitionResult pResult) {
																Window.alert("Mis à jour !");
															}
														});
									}
								});
						lView.getDeleteButton().addClickHandler(
								new ClickHandler() {
									public void onClick(ClickEvent pEvent) {
										dispatchAsync
												.execute(
														new DeleteCompetitionAction(
																lView.getCompetition()),
														new AsyncCallback<DeleteCompetitionResult>() {
															public void onFailure(
																	Throwable pCaught) {
																Window.alert("Erreur: "
																		+ pCaught
																				.getMessage());
															}

															public void onSuccess(
																	DeleteCompetitionResult pResult) {
																Window.alert("Supprimé avec succès.");
																pEventBus.fireEvent(new UpdateContentEvent(MenuItems.COMPETITION_EDITION));
															}
														});
									}
								});
						lPanel.setWidget(lView);
					}
				});
		pPanel.setWidget(lPanel);
	}

}
