package com.asptttoulousenatation.client.userspace.admin.swimmer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.core.client.ui.PopupManager;
import com.asptttoulousenatation.core.client.ui.SwimmerStatWidget;
import com.asptttoulousenatation.core.shared.swimmer.DayTimeEnum;
import com.asptttoulousenatation.core.shared.swimmer.GetAllSwimmerStatAction;
import com.asptttoulousenatation.core.shared.swimmer.GetAllSwimmerStatResult;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatComputeUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatDataUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatEnum;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;
import com.asptttoulousenatation.core.shared.swimmer.UpdateSwimmerStatAction;
import com.asptttoulousenatation.core.shared.swimmer.UpdateSwimmerStatActionData;
import com.asptttoulousenatation.core.shared.swimmer.UpdateSwimmerStatResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public class SwimmerStatActivity extends MyAbstractActivity<SwimmerStatPlace> {

	private SwimmerStatEnum kind;

	public SwimmerStatActivity(SwimmerStatPlace pPlace,
			ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		final SimplePanel lPanel = new SimplePanel();
		pPanel.setWidget(lPanel);
		switch (kind) {
		case DAY:
			openStatView(lPanel);
			break;
		case WEEK:
		case MONTH:
			openComputeView(lPanel);
			break;
		default:// Do nothing
		}

	}

	private void openStatView(final SimplePanel pPanel) {
		PopupManager.loading();
		dispatchAsync.execute(new GetAllSwimmerStatAction(kind,
				new Date(), null),
				new AsyncCallback<GetAllSwimmerStatResult<?>>() {

					public void onFailure(Throwable pCaught) {
						PopupManager.getInstance().hide();
						Window.alert("Erreur " + pCaught.getMessage());
					}

					public void onSuccess(
							GetAllSwimmerStatResult<?> pResult) {
						PopupManager.getInstance().hide();
						final SwimmerStatView lView = clientFactory
								.getSwimmerStatView((List<SwimmerStatUi>) pResult
										.getResults());
						lView.getValidButton().addClickHandler(
								new ClickHandler() {

									public void onClick(ClickEvent pEvent) {
										PopupManager.loading();
										dispatchAsync.execute(
												new UpdateSwimmerStatAction(
														getUpdateData(
																lView.getCurrentDay(),
																lView.getData())),
												new AsyncCallback<UpdateSwimmerStatResult>() {

													public void onFailure(
															Throwable pCaught) {
														PopupManager.getInstance().hide();
														Window.alert("Erreur "
																+ pCaught
																		.getMessage());
													}

													public void onSuccess(
															UpdateSwimmerStatResult pResult) {
														PopupManager.getInstance().hide();
														Window.alert("Suivi à jour");
													}
												});

									}
								});
						lView.getPreviousButton().addClickHandler(
								new ClickHandler() {

									public void onClick(ClickEvent pEvent) {
										PopupManager.loading();
										dispatchAsync
												.execute(
														new GetAllSwimmerStatAction(
																kind,
																lView.getCurrentDay(),
																false),
														new AsyncCallback<GetAllSwimmerStatResult<?>>() {

															public void onFailure(
																	Throwable pCaught) {
																PopupManager.getInstance().hide();
																Window.alert("Erreur "
																		+ pCaught
																				.getMessage());
															}

															public void onSuccess(
																	GetAllSwimmerStatResult<?> pResult) {
																PopupManager.getInstance().hide();
																lView.setCurrentDay(pResult
																		.getCurrentDay());
																lView.setCurrentDayText(pResult
																		.getCurrentDayText());
																lView.setData((List<SwimmerStatUi>) pResult
																		.getResults());
															}
														});
									}
								});
						lView.getNextButton().addClickHandler(
								new ClickHandler() {

									public void onClick(ClickEvent pEvent) {
										PopupManager.loading();
										dispatchAsync
												.execute(
														new GetAllSwimmerStatAction(
																kind,
																lView.getCurrentDay(),
																true),
														new AsyncCallback<GetAllSwimmerStatResult<?>>() {

															public void onFailure(
																	Throwable pCaught) {
																PopupManager.getInstance().hide();
																Window.alert("Erreur "
																		+ pCaught
																				.getMessage());
															}

															public void onSuccess(
																	GetAllSwimmerStatResult<?> pResult) {
																PopupManager.getInstance().hide();
																lView.setCurrentDay(pResult
																		.getCurrentDay());
																lView.setCurrentDayText(pResult
																		.getCurrentDayText());
																lView.setData((List<SwimmerStatUi>) pResult
																		.getResults());
															}
														});
									}
								});
						lView.setCurrentDay(pResult.getCurrentDay());
						lView.setCurrentDayText(pResult.getCurrentDayText());
						pPanel.setWidget(lView);

					}
				});
	}

	private void openComputeView(final SimplePanel pPanel) {
		PopupManager.loading();
		dispatchAsync
				.execute(
						new GetAllSwimmerStatAction(kind,
								new Date(), null),
						new AsyncCallback<GetAllSwimmerStatResult<?>>() {

							public void onFailure(Throwable pCaught) {
								PopupManager.getInstance().hide();
								Window.alert("Erreur " + pCaught.getMessage());
							}

							public void onSuccess(
									GetAllSwimmerStatResult<?> pResult) {
								PopupManager.getInstance().hide();
								SwimmerStatComputeView lView = clientFactory
										.getSwimmerStatComputeView((List<SwimmerStatComputeUi>) pResult
												.getResults());
								lView.setCurrentDay(pResult.getCurrentDay());
								lView.setCurrentDayText(pResult.getCurrentDayText());
								pPanel.setWidget(lView);
							}
						});
	}

	public SwimmerStatEnum getKind() {
		return kind;
	}

	public void setKind(SwimmerStatEnum pKind) {
		kind = pKind;
	}

	private List<UpdateSwimmerStatActionData> getUpdateData(Date pDay,
			List<SwimmerStatWidget> pWidgets) {
		List<UpdateSwimmerStatActionData> lUpdateData = new ArrayList<UpdateSwimmerStatActionData>(
				pWidgets.size());
		for (SwimmerStatWidget lWidget : pWidgets) {
			SwimmerStatUi lSwimmerStat = lWidget.getSwimmerStat();
			UpdateSwimmerStatActionData lUpdateD = new UpdateSwimmerStatActionData();
			if (lSwimmerStat.getData() != null
					&& lSwimmerStat.getData().size() >= 1) {
				// Morning
				SwimmerStatDataUi lStatDataUi = lSwimmerStat.getData().get(0);
				lUpdateD.setId(lStatDataUi.getId());
			}
			lUpdateD.setUser(lSwimmerStat.getUser());
			lUpdateD.setDay(pDay);
			lUpdateD.setDayTime(DayTimeEnum.MATIN);
			lUpdateD.setDistance(Integer.valueOf(lWidget.getMorning()
					.getValue()));
			lUpdateD.setComment(lWidget.getComment().getValue());
			lUpdateData.add(lUpdateD);

			lUpdateD = new UpdateSwimmerStatActionData();

			if (lSwimmerStat.getData() != null
					&& lSwimmerStat.getData().size() >= 2) {
				// Morning
				SwimmerStatDataUi lStatDataUi = lSwimmerStat.getData().get(1);
				lUpdateD.setId(lStatDataUi.getId());
			}
			lUpdateD.setUser(lSwimmerStat.getUser());
			lUpdateD.setDay(pDay);
			lUpdateD.setDayTime(DayTimeEnum.MIDI);
			lUpdateD.setDistance(Integer
					.valueOf(lWidget.getMidday().getValue()));
			lUpdateData.add(lUpdateD);

			lUpdateD = new UpdateSwimmerStatActionData();
			if (lSwimmerStat.getData() != null
					&& lSwimmerStat.getData().size() >= 3) {
				// Morning
				SwimmerStatDataUi lStatDataUi = lSwimmerStat.getData().get(2);
				lUpdateD.setId(lStatDataUi.getId());
			}
			lUpdateD.setUser(lSwimmerStat.getUser());
			lUpdateD.setDay(pDay);
			lUpdateD.setDayTime(DayTimeEnum.SOIR);
			lUpdateD.setDistance(Integer.valueOf(lWidget.getNight().getValue()));
			lUpdateData.add(lUpdateD);

			lUpdateD = new UpdateSwimmerStatActionData();
			if (lSwimmerStat.getData() != null
					&& lSwimmerStat.getData().size() >= 4) {
				// Morning
				SwimmerStatDataUi lStatDataUi = lSwimmerStat.getData().get(3);
				lUpdateD.setId(lStatDataUi.getId());
			}
			lUpdateD.setUser(lSwimmerStat.getUser());
			lUpdateD.setDay(pDay);
			lUpdateD.setDayTime(DayTimeEnum.MUSCU);
			lUpdateD.setDistance(Integer.valueOf(lWidget.getBodybuilding()
					.getValue()));
			lUpdateData.add(lUpdateD);
		}
		return lUpdateData;
	}
}