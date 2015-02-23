package com.asptttoulousenatation.client.userspace.home;

import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class HomeActivity extends MyAbstractActivity<HomePlace> {

	private InitUserSpaceResult initResult;
	
	public HomeActivity(HomePlace pPlace, ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}
	
	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		List<MenuUi> lMenuShorcut = new ArrayList<MenuUi>();
		for(AreaUi lArea: initResult.getArea().values()) {
			if(lArea.isShortcut()) {
				for(MenuUi lMenu: lArea.getMenuSet().values()) {
					if(lMenu.isShortcut()) {
						lMenuShorcut.add(lMenu);
					}
				}
			}
		}
		HomeView lHomeView = clientFactory.getHomeView(lMenuShorcut);
		pPanel.setWidget(lHomeView);
	}
	
	@Override
	public String mayStop() {
		return "Home activity will be stop";
	}

	public void setUserSpaceResult(InitUserSpaceResult pUserSpaceResult) {
		initResult = pUserSpaceResult;
	}
}