package com.asptttoulousenatation.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class AlternateBanner extends Composite {

	private SimplePanel panel;
	private int index;
	private static final String[] IMAGES_URL = {"images/img01.jpg", "images/img02.jpg", "images/img03.jpeg"};
	
	public AlternateBanner() {
		panel = new SimplePanel();
		initWidget(panel);
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
				image.setHeight("200px");
				image.setWidth(image.getHeight() * ratio + "px");
				panel.clear();
				panel.add(image);
				index = (index + 1) % IMAGES_URL.length;
				
			}
		};
		timer.scheduleRepeating(3000);
	}
}
