package com.asptttoulousenatation.client.userspace.admin.actu;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.Date;

import com.asptttoulousenatation.core.client.ui.EditorToolbar;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
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
		int rowIndex = 0;
		// Input
		FlexTable lPanel = new FlexTable();
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		// Title
		title = new TextBox();
		title.setWidth("150px");
		lPanel.setWidget(rowIndex, 0, createLabel("Titre"));
		lPanel.setWidget(rowIndex, 1, title);
		rowIndex++;
		
		// Summary
		summary = new TextBox();
		summary.setWidth("300px");
		lPanel.setWidget(rowIndex, 0, createLabel("Description courte"));
		lPanel.setWidget(rowIndex, 1, summary);
		rowIndex++;

		// Date
		date = new DateBox();
		date.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd MMMM yyyy HH:mm:ss")));
		date.setValue(new Date());
		date.setWidth("200px");
		lPanel.setWidget(rowIndex, 0, createLabel("Date"));
		lPanel.setWidget(rowIndex, 1, date);
		rowIndex++;

		// Contenu
		// Add the components to a panel
		// Content
		contentInput = new CKEditor(new EditorToolbar());
		lPanel.setWidget(rowIndex, 0, createLabel("Actualité"));
		lPanel.setWidget(rowIndex, 1, contentInput);
		rowIndex++;

		publishButton = new Button();
		publishButton.setTitle("Publier");
		publishButton.setStyleName(CSS.addButton());
		lPanel.setWidget(rowIndex, 0, publishButton);
		lCellFormatter.setColSpan(rowIndex, 0, 2);
		lCellFormatter.setHorizontalAlignment(rowIndex, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		rowIndex++;

		errorMessage = new Label();
		panel.add(errorMessage);
		panel.add(lPanel);
	}
	
	public void init() {
		title.setValue("");
		date.setValue(new Date());
		contentInput.setData("");
	}

	public HasClickHandlers getPublishButton() {
		return publishButton;
	}

	public HasValue<String> getTitre() {
		return title;
	}

	public String getContent() {
		return contentInput.getHTML();
	}

	public HasValue<Date> getCreationDate() {
		return date;
	}

	private Label createLabel(String pLabel) {
		Label lLabel = new Label(pLabel);
		lLabel.setStyleName(CSS.userSpaceContentLabel());
		return lLabel;
	}

	public HasValue<String> getSummary() {
		return summary;
	}
}