package com.asptttoulousenatation.client;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import static com.asptttoulousenatation.client.resources.ASPTT_ProtoResources.IMAGES;

public class AlternateBanner extends Composite {

	private SimplePanel panel;
	private int index;
	private static final ImageResource[] IMAGES_URL = {IMAGES.img01(), IMAGES.img02(), IMAGES.img03()};
	
	public AlternateBanner() {
		panel = new SimplePanel();
		initWidget(panel);
		index = 0;
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				Image image = new Image(IMAGES_URL[index]);
				
				panel.clear();
				panel.add(image);
				index = (index + 1) % IMAGES_URL.length;
				
			}
		};
		timer.scheduleRepeating(3000);
	}
}
