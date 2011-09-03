package com.asptttoulousenatation.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class AlternateBanner_Part extends Composite {

	private Panel panel;
	private int index;
	private static final String[] IMAGES_URL = {"images/logo_omnisport.jpg", "images/logoarena.gif"};
	
	public AlternateBanner_Part() {
		panel = new SimplePanel();
		initWidget(panel);
		panel.getElement().getStyle().setMarginTop(10, Unit.PX);
		index = 0;
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				Image image = new Image("http://prototype.asptt-toulouse-natation.com/" + IMAGES_URL[index]);
				int ratio;
				if(image.getHeight() == 0 || image.getWidth() == 0) {
					ratio = 1;
				}
				else {
					ratio = image.getWidth() / image.getHeight();
				}
//				image.setWidth("200px");
//				image.setHeight(image.getWidth() * ratio + "px");
				panel.clear();
				panel.add	(image);
				index = (index + 1) % IMAGES_URL.length;
				
			}
		};
		timer.scheduleRepeating(3000);
	}
}
