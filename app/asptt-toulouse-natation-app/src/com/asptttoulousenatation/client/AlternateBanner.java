package com.asptttoulousenatation.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class AlternateBanner extends Composite {

	private SimplePanel panel;
	private int index;
	
	private String[] photos;
	
	public AlternateBanner(String[] pPhotos) {
		photos = pPhotos;
		panel = new SimplePanel();
		initWidget(panel);
		index = 0;
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				Image image = new Image(photos[index]);
				
				panel.clear();
				panel.add(image);
				index = (index + 1) % photos.length;
				
			}
		};
		timer.scheduleRepeating(3000);
	}
}
