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
import com.asptttoulousenatation.client.userspace.admin.club.group.GroupView;
import com.asptttoulousenatation.client.userspace.admin.club.group.GroupViewImpl;
import com.asptttoulousenatation.client.userspace.admin.club.slot.SlotView;
import com.asptttoulousenatation.client.userspace.admin.club.slot.SlotViewImpl;
import com.asptttoulousenatation.client.userspace.admin.competition.CompetitionView;
import com.asptttoulousenatation.client.userspace.admin.competition.CompetitionViewImpl;
import com.asptttoulousenatation.client.userspace.admin.structure.area.AreaView;
import com.asptttoulousenatation.client.userspace.admin.structure.area.AreaViewImpl;
import com.asptttoulousenatation.client.userspace.admin.user.UserCreationView;
import com.asptttoulousenatation.client.userspace.admin.user.UserCreationViewImpl;
import com.asptttoulousenatation.client.userspace.admin.user.UserEditionView;
import com.asptttoulousenatation.client.userspace.admin.user.UserEditionViewImpl;
import com.asptttoulousenatation.client.userspace.home.HomeView;
import com.asptttoulousenatation.client.userspace.home.HomeViewImpl;
import com.asptttoulousenatation.client.userspace.menu.MenuView;
import com.asptttoulousenatation.client.userspace.menu.MenuViewImpl;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.core.shared.competition.CompetitionUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

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
	private UserEditionView userEditionView;
	private UserCreationView userCreationView;
	
	public HomeView getHomeView(List<MenuUi> pMenuList) {
		return new HomeViewImpl(pMenuList);
	}

	public MenuView getMenuView(InitUserSpaceResult pInitUserSpaceResult) {
		return new MenuViewImpl(pInitUserSpaceResult);
	}

	public UserSpaceView getAdminView(InitUserSpaceResult pInitUserSpaceResult) {
		return new UserSpaceViewImpl(pInitUserSpaceResult);
	}

	public ActuView getActuView() {
		return actuView;
	}

	public AreaView getAreaView(List<MenuUi> pData) {
		areaView = new AreaViewImpl(pData);
		return areaView;
	}

	public ActuEditionView getActuEditionView(List<ActuUi> pData) {
		actuEditionView = new ActuEditionViewImpl(pData);
		return actuEditionView;
	}

	public UserEditionView getUserEditionView(List<UserUi> pData, List<SlotUi> pSlotData) {
		userEditionView = new UserEditionViewImpl(pData, pSlotData);
		return userEditionView;
	}

	public UserCreationView getUserCreationView(List<SlotUi> pSlotData) {
		userCreationView = new UserCreationViewImpl(pSlotData);
		return userCreationView;
	}

	public GroupView getGroupView(List<GroupUi> pGroups) {
		return new GroupViewImpl(pGroups);
	}

	public SlotView getSlotView(List<SlotUi> pSlots, List<GroupUi> pGroups) {
		return new SlotViewImpl(pSlots, pGroups);
	}

	public CompetitionView getCompetitionView(
			List<CompetitionUi> pCompetitionUis) {
		return new CompetitionViewImpl(pCompetitionUis);
	}
}