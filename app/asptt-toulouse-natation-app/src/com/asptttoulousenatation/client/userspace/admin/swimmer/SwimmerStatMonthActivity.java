package com.asptttoulousenatation.client.userspace.admin.swimmer;

import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.core.client.ui.PopupManager;
import com.asptttoulousenatation.core.shared.swimmer.GetAllSwimmerStatAction;
import com.asptttoulousenatation.core.shared.swimmer.GetAllSwimmerStatResult;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatEnum;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatMonthUi;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

public class SwimmerStatMonthActivity extends MyAbstractActivity<SwimmerStatPlace> {

	private SwimmerStatEnum kind;

	public SwimmerStatMonthActivity(SwimmerStatPlace pPlace,
			ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		final SimplePanel lPanel = new SimplePanel();
		pPanel.setWidget(lPanel);
		PopupManager.loading();
		dispatchAsync.execute(new GetAllSwimmerStatAction(kind, new Date(),
				null), new AsyncCallback<GetAllSwimmerStatResult<?>>() {

			public void onFailure(Throwable pCaught) {
				PopupManager.getInstance().hide();
				Window.alert("Erreur " + pCaught.getMessage());
			}

			public void onSuccess(GetAllSwimmerStatResult<?> pResult) {
				PopupManager.getInstance().hide();
				final SwimmerStatMonthView lView = clientFactory
						.getSwimmerStatMonthView((List<SwimmerStatMonthUi>) pResult
								.getResults());
				lView.getPreviousButton().addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent pEvent) {
						PopupManager.loading();
						dispatchAsync.execute(
								new GetAllSwimmerStatAction(kind, lView
										.getCurrentDay(), false),
								new AsyncCallback<GetAllSwimmerStatResult<?>>() {

									public void onFailure(Throwable pCaught) {
										PopupManager.getInstance().hide();
										Window.alert("Erreur "
												+ pCaught.getMessage());
									}

									public void onSuccess(
											GetAllSwimmerStatResult<?> pResult) {
										PopupManager.getInstance().hide();
										lView.setCurrentDay(pResult
												.getCurrentDay());
										lView.setCurrentDayText(pResult
												.getCurrentDayText());
										lView.setData((List<SwimmerStatMonthUi>) pResult
												.getResults());
									}
								});
					}
				});
				lView.getNextButton().addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent pEvent) {
						PopupManager.loading();
						dispatchAsync.execute(
								new GetAllSwimmerStatAction(kind, lView
										.getCurrentDay(), true),
								new AsyncCallback<GetAllSwimmerStatResult<?>>() {

									public void onFailure(Throwable pCaught) {
										PopupManager.getInstance().hide();
										Window.alert("Erreur "
												+ pCaught.getMessage());
									}

									public void onSuccess(
											GetAllSwimmerStatResult<?> pResult) {
										PopupManager.getInstance().hide();
										lView.setCurrentDay(pResult
												.getCurrentDay());
										lView.setCurrentDayText(pResult
												.getCurrentDayText());
										lView.setData((List<SwimmerStatMonthUi>) pResult
												.getResults());
									}
								});
					}
				});
				lView.setCurrentDay(pResult.getCurrentDay());
				lView.setCurrentDayText(pResult.getCurrentDayText());
				lPanel.setWidget(lView);
			}
		});
	}

	public SwimmerStatEnum getKind() {
		return kind;
	}

	public void setKind(SwimmerStatEnum pKind) {
		kind = pKind;
	}
}