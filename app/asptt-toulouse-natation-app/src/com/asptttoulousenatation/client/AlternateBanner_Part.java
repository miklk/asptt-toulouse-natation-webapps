package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;
import static com.asptttoulousenatation.client.resources.ASPTT_ProtoResources.IMAGES;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class AlternateBanner_Part extends Composite {

	private HorizontalPanel panel;
	private int index;
	private static final ImageResource [] IMAGES_URL = {IMAGES.logoArena(), IMAGES.logoAcadomia(), IMAGES.logoNataquashop(), IMAGES.logoCD31(), IMAGES.logoCRMP(), IMAGES.logoFfn(), IMAGES.logoFsasptt(), IMAGES.logoMairietoulouse()};
	private static final String [] URL = {"http://www.arena.fr", "http://www.acadomia.fr", "http://www.nataquashop.com", "http://hautegaronne.ffnatation.fr/", "http://midipyrenees.ffnatation.fr/", "http://www.ffnatation.fr/", "http://asptt.com/", "http://www.toulouse.fr/"};
	
	public AlternateBanner_Part() {
		panel = new HorizontalPanel();
		initWidget(panel);
		panel.setStyleName(CSS.partnerPanel());
		index = 0;
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				Image image = new Image(IMAGES_URL[index]);
				final String url = URL[index];
				if(!url.isEmpty()) {
				image.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						Window.open(url, "_", "");
					}
				});
				}
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
