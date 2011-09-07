package com.asptttoulousenatation.client.userspace.admin.actu;

import java.util.Date;

import com.axeiya.gwtckeditor.client.CKConfig;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

public class ActuViewImpl extends Composite implements ActuView {

	private HorizontalPanel panel;
	private TextBox title;
	private TextBox summary;
	private DateBox date;
	private CKEditor contentInput;
	private Button publishButton;
	private Label errorMessage;
	
	public ActuViewImpl() {
		panel = new HorizontalPanel();
		initWidget(panel);
		//Input
		FlexTable lPanel = new FlexTable();
		//Title
		title = new TextBox();
		title.setWidth("300px");
		lPanel.setWidget(0, 0, new Label("Titre"));
		lPanel.setWidget(0, 1, title);
		
		//Summary
		summary = new TextBox();
		summary.setWidth("300px");
		lPanel.setWidget(1, 0, new Label("Description courte"));
		lPanel.setWidget(1, 1, summary);
		
		//Date
		date = new DateBox();
		date.setValue(new Date());
		date.setWidth("200px");
		lPanel.setWidget(2, 0, new Label("Date"));
		lPanel.setWidget(2, 1, date);
		
		//Contenu
	    // Add the components to a panel
		// Content
		contentInput = new CKEditor(CKConfig.full);
		contentInput.setSize("95%", "400px");
		lPanel.setWidget(3, 0, new Label("Actualité"));
		lPanel.setWidget(3, 1, contentInput);

		publishButton = new Button("Publier");
		publishButton.setWidth("300px");
		publishButton.setHeight("50px");
		lPanel.setWidget(4, 0, publishButton);
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lCellFormatter.setColSpan(4, 0, 2);
		lCellFormatter.setHorizontalAlignment(4, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		
		
		errorMessage = new Label();
		panel.add(errorMessage);
		panel.add(lPanel);
	}
	
	public HasClickHandlers getCloseButton() {
		// TODO Auto-generated method stub
		return null;
	}

	public HasClickHandlers getSaveButton() {
		// TODO Auto-generated method stub
		return null;
	}

	public HasClickHandlers getPublishButton() {
		return publishButton;
	}

	public HasValue<String> getTitre() {
		return title;
	}

	public HasValue<String> getSummary() {
		return summary;
	}

	public String getContent() {
		return contentInput.getHTML();
	}

	public HasValue<Date> getCreationDate() {
		return date;
	}
}