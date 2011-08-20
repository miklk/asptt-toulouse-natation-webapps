package com.asptttoulousenatation.client.userspace;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import com.asptttoulousenatation.core.client.ui.SimpleLayoutPanel;
import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class UserSpaceViewImpl extends ResizeComposite implements UserSpaceView {

	private DockLayoutPanel panel;
	private Label titleLabel;
	private SimpleLayoutPanel menuPanel;
	private SimpleLayoutPanel contentPanel;
	private LayoutPanel headerPanel;
	private Button goBackPublic;
	
	private InitUserSpaceResult initUserSpaceResult;
	
	public UserSpaceViewImpl(InitUserSpaceResult pInitUserSpaceResult) {
		initUserSpaceResult = pInitUserSpaceResult;
		panel = new DockLayoutPanel(Unit.PCT);
		initWidget(panel);
		buildHeader();
		panel.addNorth(headerPanel, 20);
		menuPanel = new SimpleLayoutPanel();
		panel.addWest(menuPanel, 20);
		contentPanel = new SimpleLayoutPanel();
		panel.add(contentPanel);
	}
	
	private void buildHeader() {
		headerPanel = new LayoutPanel();
		headerPanel.addStyleName(CSS.userHeader());
		titleLabel = new Label();
		headerPanel.add(titleLabel);
		
		goBackPublic = new Button("Retourner à l'espace public");
		goBackPublic.addStyleName(CSS.goBackPublicButton());
		headerPanel.add(goBackPublic);
		
		headerPanel.setWidgetLeftWidth(titleLabel, 0, Unit.PCT, 80, Unit.PCT);
		headerPanel.setWidgetLeftWidth(goBackPublic, 80, Unit.PCT, 10, Unit.PCT);
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