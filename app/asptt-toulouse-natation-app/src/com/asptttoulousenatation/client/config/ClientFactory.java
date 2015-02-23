package com.asptttoulousenatation.client.config;

import java.util.List;

import com.asptttoulousenatation.client.MainView;
import com.asptttoulousenatation.client.userspace.UserSpaceView;
import com.asptttoulousenatation.client.userspace.admin.actu.ActuEditionView;
import com.asptttoulousenatation.client.userspace.admin.actu.ActuView;
import com.asptttoulousenatation.client.userspace.admin.club.group.GroupView;
import com.asptttoulousenatation.client.userspace.admin.club.slot.SlotView;
import com.asptttoulousenatation.client.userspace.admin.structure.area.AreaView;
import com.asptttoulousenatation.client.userspace.home.HomeView;
import com.asptttoulousenatation.client.userspace.menu.MenuView;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Command;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
	public EventBus getEventBus();
	public PlaceController getPlaceController();
	public MainView getMainView(InitResult pInitResult, UserUi pUser, EventBus pEventBus);
	
	
	
	public UserSpaceView getAdminView();
	public HomeView getHomeView(List<MenuUi> pMenuList);
	public MenuView getMenuView(InitUserSpaceResult pInitUserSpaceResult, Command pStackPanelShowCommand);
	public ActuView getActuView();
	public AreaView getAreaView(AreaUi pArea);
	public ActuEditionView getActuEditionView(List<ActuUi> pData);
	public GroupView getGroupView(List<GroupUi> pGroups);
	public SlotView getSlotView(List<SlotUi> pSlots, List<GroupUi> pGroups);
}