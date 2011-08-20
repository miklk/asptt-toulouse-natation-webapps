package com.asptttoulousenatation.client.userspace.admin.actu;

import java.util.Date;

import com.asptttoulousenatation.core.client.ui.InputPanel;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RichTextToolbar;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.datepicker.client.DateBox;

public class ActuViewImpl extends ResizeComposite implements ActuView {

	private LayoutPanel panel;
	private TextBox title;
	private TextBox summary;
	private DateBox date;
	private RichTextArea content;
	private Button publishButton;
	private Label errorMessage;
	
	public ActuViewImpl() {
		panel = new LayoutPanel();
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
		content = new RichTextArea();
	    content.setSize("100%", "14em");
		RichTextToolbar lRichTextToolbar = new RichTextToolbar(content);
	    lRichTextToolbar.setWidth("100%");
		Grid lRichText = new Grid(2, 1);
		lRichText.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		lRichText.getElement().getStyle().setBorderColor("#CCCCCC");
		lRichText.getElement().getStyle().setBorderWidth(1, Unit.PX);
		lRichText.setWidget(0, 0, lRichTextToolbar);
		lRichText.setWidget(1, 0, content);
		lPanel.setWidget(3, 0, new Label("Actu"));
		lPanel.setWidget(3, 1, lRichText);

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
		panel.setWidgetTopHeight(lPanel, 1, Unit.PCT, 100, Unit.PCT);
//		panel.add(lTitleInputPanel);
//		panel.add(lSummaryInputPanel);
//		panel.add(lDateInputPanel);
//		panel.add(lContentInputPanel);
//		panel.add(publishButton);
//		panel.setWidgetTopHeight(lTitleInputPanel, 1, Unit.PCT, 10, Unit.PCT);
//		panel.setWidgetTopHeight(lSummaryInputPanel, 12, Unit.PCT, 10, Unit.PCT);
//		panel.setWidgetTopHeight(lDateInputPanel, 32, Unit.PCT, 10, Unit.PCT);
//		panel.setWidgetTopHeight(lContentInputPanel, 52, Unit.PCT, 10, Unit.PCT);
//		panel.setWidgetLeftWidth(publishButton, 30, Unit.PCT, 20, Unit.PCT);
//		panel.setWidgetTopHeight(publishButton, 74, Unit.PCT, 10, Unit.PCT);
		panel.setWidgetVerticalPosition(errorMessage, Alignment.END);
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
		return content.getHTML();
	}

	public HasValue<Date> getCreationDate() {
		return date;
	}
}