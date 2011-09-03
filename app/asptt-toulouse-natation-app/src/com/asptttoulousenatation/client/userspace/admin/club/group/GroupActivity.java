package com.asptttoulousenatation.client.userspace.admin.club.group;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.core.client.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.club.group.CreateGroupAction;
import com.asptttoulousenatation.core.shared.club.group.CreateGroupResult;
import com.asptttoulousenatation.core.shared.club.group.GetAllGroupAction;
import com.asptttoulousenatation.core.shared.club.group.GetAllGroupResult;
import com.asptttoulousenatation.core.shared.club.group.UpdateGroupAction;
import com.asptttoulousenatation.core.shared.club.group.UpdateGroupResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public class GroupActivity extends MyAbstractActivity<GroupPlace> {

	public GroupActivity(GroupPlace pPlace, ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		final SimplePanel lPanel = new SimplePanel();
		dispatchAsync.execute(new GetAllGroupAction(), new AsyncCallback<GetAllGroupResult>() {

			public void onFailure(Throwable pCaught) {
				Window.alert("Erreur: " + pCaught.getMessage());
			}

			public void onSuccess(GetAllGroupResult pResult) {
				final GroupView lGroupView = new GroupViewImpl(pResult.getGroups());
				lGroupView.getCreateButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						dispatchAsync.execute(new CreateGroupAction(lGroupView.getGroupTitle().getValue()), new AsyncCallback<CreateGroupResult>() {

							public void onFailure(Throwable pCaught) {
								Window.alert("Erreur " + pCaught.getMessage());
							}

							public void onSuccess(CreateGroupResult pResult) {
								Window.alert("Créé");
							}
						});
					}
				});
				
				lGroupView.getUpdateButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						dispatchAsync.execute(new UpdateGroupAction(lGroupView.getGroupId(), lGroupView.getGroupTitle().getValue()), new AsyncCallback<UpdateGroupResult>() {

							public void onFailure(Throwable pCaught) {
								Window.alert("Erreur " + pCaught.getMessage());
							}

							public void onSuccess(UpdateGroupResult pResult) {
								Window.alert("Mis à jour");
							}
						});
					}
				});
				
				lPanel.setWidget(lGroupView);
			}
		});
		pPanel.setWidget(lPanel);

	}

}
