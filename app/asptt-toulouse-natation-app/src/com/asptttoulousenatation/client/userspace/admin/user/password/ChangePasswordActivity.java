package com.asptttoulousenatation.client.userspace.admin.user.password;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.user.ChangePasswordAction;
import com.asptttoulousenatation.core.shared.user.ChangePasswordResult;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class ChangePasswordActivity extends
		MyAbstractActivity<ChangePasswordPlace> {
	
	private UserUi user;

	public ChangePasswordActivity(ChangePasswordPlace pPlace,
			ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	@Override
	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		final ChangePasswordView lView = clientFactory.getChangePasswordView();
		lView.getValidButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent pEvent) {
				dispatchAsync.execute(new ChangePasswordAction(getUser().getEmailAddress(), lView.getOldPassword().getValue(), lView.getNewPassword().getValue()), new AsyncCallback<ChangePasswordResult>() {

					public void onFailure(Throwable pCaught) {
						//Error
					}

					public void onSuccess(ChangePasswordResult pResult) {
						if(pResult.isResult()) {
							Window.alert("Votre mot de passe a été modifié, un e-mail de confirmation vous a été envoyé");
						} else {
							if(pResult.isNotSame()) {
								Window.alert("Impossible de modifier le mot de passe, l'ancien mot de passe ne correspond pas.");
							} else {
								Window.alert("Impossible de modifier le mot de passe.");
							}
						}
					}
				});
			}
		});
		pPanel.setWidget(lView);
	}

	public UserUi getUser() {
		return user;
	}

	public void setUser(UserUi pUser) {
		user = pUser;
	}
}