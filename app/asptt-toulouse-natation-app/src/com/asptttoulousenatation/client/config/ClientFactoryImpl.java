package com.asptttoulousenatation.client.config;

import java.util.List;

import com.asptttoulousenatation.client.MainView;
import com.asptttoulousenatation.client.MainViewImpl;
import com.asptttoulousenatation.client.userspace.UserSpaceView;
import com.asptttoulousenatation.client.userspace.UserSpaceViewImpl;
import com.asptttoulousenatation.client.userspace.admin.actu.ActuEditionView;
import com.asptttoulousenatation.client.userspace.admin.actu.ActuEditionViewImpl;
import com.asptttoulousenatation.client.userspace.admin.actu.ActuView;
import com.asptttoulousenatation.client.userspace.admin.actu.ActuViewImpl;
import com.asptttoulousenatation.client.userspace.admin.structure.area.AreaView;
import com.asptttoulousenatation.client.userspace.admin.structure.area.AreaViewImpl;
import com.asptttoulousenatation.client.userspace.home.HomeView;
import com.asptttoulousenatation.client.userspace.home.HomeViewImpl;
import com.asptttoulousenatation.client.userspace.menu.MenuView;
import com.asptttoulousenatation.client.userspace.menu.MenuViewImpl;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Command;
import com.google.web.bindery.event.shared.EventBus;

public class ClientFactoryImpl implements ClientFactory {

	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	
	private MainView mainView;
	
	public EventBus getEventBus() {
		return eventBus;
	}

	public PlaceController getPlaceController() {
		return placeController;
	}

	public MainView getMainView(InitResult pInitResult, UserUi pUser, EventBus pEventBus) {
		mainView = new MainViewImpl(pInitResult, pUser, pEventBus);
		return mainView;
	}
	
	private final ActuView actuView = new ActuViewImpl();
	private AreaView areaView;
	private ActuEditionView actuEditionView;
	
	public HomeView getHomeView(List<MenuUi> pMenuList) {
		return new HomeViewImpl(pMenuList);
	}

	public MenuView getMenuView(InitUserSpaceResult pInitUserSpaceResult, Command pStackPanelShowCommand) {
		return new MenuViewImpl(pInitUserSpaceResult, pStackPanelShowCommand);
	}

	public UserSpaceView getAdminView() {
		return new UserSpaceViewImpl();
	}

	public ActuView getActuView() {
		return actuView;
	}

	public AreaView getAreaView(AreaUi pArea) {
		areaView = new AreaViewImpl(pArea);
		return areaView;
	}

	public ActuEditionView getActuEditionView(List<ActuUi> pData) {
		actuEditionView = new ActuEditionViewImpl(pData);
		return actuEditionView;
	}
}