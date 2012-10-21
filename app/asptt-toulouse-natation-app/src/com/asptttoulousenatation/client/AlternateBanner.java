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
		panel.setHeight("291px");
		initWidget(panel);
		index = 0;
		Timer timer = new Timer() {

			@Override
			public void run() {
				if (photos != null && photos.length > index) {
					Image image = new Image(photos[index]);

					//Ratio
					float ratio = image.getWidth() / 290;
					image.setHeight("290px");
					image.setWidth(ratio * 290 + "px");
					panel.clear();
					panel.add(image);
					index = (index + 1) % photos.length;
				}
			}
		};
		timer.scheduleRepeating(3000);
	}
}
