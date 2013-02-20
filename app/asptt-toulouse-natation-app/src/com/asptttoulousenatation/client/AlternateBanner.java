package com.asptttoulousenatation.client;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class AlternateBanner extends Composite {

	private static int IMAGE_HEIGHT = 270;
	private HorizontalPanel panel;
	private int index;
	private int oldIndex;

	private String[] photos;
	private Image[] images;

	public AlternateBanner(String[] pPhotos) {
		images = new Image[pPhotos.length];
		photos = pPhotos;
		panel = new HorizontalPanel();
		panel.setWidth("100%");
		panel.setHeight("291px");
		initWidget(panel);
		if(pPhotos != null && pPhotos.length >0) {
		index = 0;
		oldIndex = 0;
		for (int i = 0; i < photos.length; i++) {
			String url = photos[i];
			Image image = new Image(url);
			image.getElement().getStyle().setPosition(Position.ABSOLUTE);
			image.getElement().getStyle().setLeft(30, Unit.PCT);

			// Ratio
			float ratio = image.getWidth() / IMAGE_HEIGHT;
			// image.setHeight(IMAGE_HEIGHT + "px");
			// image.setWidth(ratio * IMAGE_HEIGHT+ "px");
			image.getElement().getStyle().setVisibility(Visibility.HIDDEN);
			images[i] = image;
			panel.add(images[i]);
		}
		if (images != null && images.length > 0) {
			images[0].getElement().getStyle().setVisibility(Visibility.VISIBLE);
		}

		Timer timer = new Timer() {

			@Override
			public void run() {
				for (int i = 0; i < images.length; i++) {
					images[i].getElement().getStyle()
							.setVisibility(Visibility.HIDDEN);
					images[i].getElement().getStyle().setZIndex(-1);
				}
				images[index].getElement().getStyle()
						.setVisibility(Visibility.VISIBLE);
				images[index].getElement().getStyle().setZIndex(1);
				index = (index + 1) % photos.length;
			}
		};
		timer.scheduleRepeating(5000);
	}
	}
}
