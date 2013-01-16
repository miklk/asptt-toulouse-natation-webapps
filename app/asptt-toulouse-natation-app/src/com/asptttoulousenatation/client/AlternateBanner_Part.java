package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;
import static com.asptttoulousenatation.client.resources.ASPTT_ProtoResources.IMAGES;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class AlternateBanner_Part extends Composite {

	private HorizontalPanel panel;
	private int index;
	private static final ImageResource [] IMAGES_URL = {IMAGES.logoArena(), IMAGES.logoOmnisport()};
	
	public AlternateBanner_Part() {
		panel = new HorizontalPanel();
		initWidget(panel);
		panel.setStyleName(CSS.partnerPanel());
		index = 0;
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				Image image = new Image(IMAGES_URL[index]);
				panel.clear();
				panel.add(image);
				panel.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);
				panel.setCellVerticalAlignment(image, HasVerticalAlignment.ALIGN_MIDDLE);
				index = (index + 1) % IMAGES_URL.length;
				
			}
		};
		timer.scheduleRepeating(3000);
	}
}
