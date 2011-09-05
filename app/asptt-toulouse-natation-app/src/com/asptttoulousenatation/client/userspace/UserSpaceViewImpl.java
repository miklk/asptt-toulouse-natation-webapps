package com.asptttoulousenatation.client.userspace;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class UserSpaceViewImpl extends Composite implements UserSpaceView {

	private DockPanel panel;
	private Label titleLabel;
	private SimplePanel menuPanel;
	private SimplePanel contentPanel;
	private HorizontalPanel headerPanel;
	private Button goBackPublic;
	
	private InitUserSpaceResult initUserSpaceResult;
	
	public UserSpaceViewImpl(InitUserSpaceResult pInitUserSpaceResult) {
		initUserSpaceResult = pInitUserSpaceResult;
		panel = new DockPanel();
		initWidget(panel);
		panel.setStyleName(CSS.userSpacePage());
		buildHeader();
		panel.add(headerPanel, DockPanel.NORTH);
		menuPanel = new SimplePanel();
		panel.add(menuPanel, DockPanel.WEST);
		contentPanel = new SimplePanel();
		panel.add(contentPanel, DockPanel.CENTER);
		
		headerPanel.setStyleName(CSS.userSpaceHeader());
		menuPanel.setStyleName(CSS.userSpaceMenu());
		contentPanel.setStyleName(CSS.userSpaceContent());
	}
	
	private void buildHeader() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName(CSS.userHeader());
		titleLabel = new Label();
		headerPanel.add(titleLabel);
		
		goBackPublic = new Button("Retourner à l'espace public");
		goBackPublic.addStyleName(CSS.goBackPublicButton());
		headerPanel.add(goBackPublic);
		
//		headerPanel.setWidgetLeftWidth(titleLabel, 0, Unit.PCT, 80, Unit.PCT);
//		headerPanel.setWidgetLeftWidth(goBackPublic, 80, Unit.PCT, 10, Unit.PCT);
	}
	
	public void setMenu(Widget pWidget) {
		menuPanel.setWidget(pWidget);
	}

	public void setContent(Widget pWidget) {
		contentPanel.setWidget(pWidget);
	}

	public void setUserName(String pUserName) {
		titleLabel.setText("Bienvenue sur l'interface d'administration " + pUserName);
	}

	public AcceptsOneWidget getMenuPanel() {
		return menuPanel;
	}

	public AcceptsOneWidget getContentPanel() {
		return contentPanel;
	}

	public HasClickHandlers getPublicButton() {
		return goBackPublic;
	}
}