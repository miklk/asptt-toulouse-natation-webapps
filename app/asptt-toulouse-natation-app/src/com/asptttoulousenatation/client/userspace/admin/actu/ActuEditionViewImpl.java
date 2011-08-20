package com.asptttoulousenatation.client.userspace.admin.actu;

import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.core.client.ui.InputPanel;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class ActuEditionViewImpl extends ResizeComposite implements ActuEditionView {

	private LayoutPanel panel;
	private List<ActuUi> data;
	private CellList<ActuUi> cellList;
	private SingleSelectionModel<ActuUi> selectionModel;
	private SimpleLayoutPanel actuEditionPanel;
	
	private TextBox title;
	private TextBox summary;
	private DateBox date;
	private RichTextArea content;
	private Button updateButton;
	
	public ActuEditionViewImpl(List<ActuUi> pData) {
		data = pData;
		panel = new LayoutPanel();
		initWidget(panel);
		cellList = new CellList<ActuUi>(new ActuCell());
		cellList.setRowData(data);
		panel.add(cellList);
		selectionModel = new SingleSelectionModel<ActuUi>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			public void onSelectionChange(SelectionChangeEvent pEvent) {
				buildActuEditionPanel(selectionModel.getSelectedObject());
				
			}
		});
		
		actuEditionPanel = new SimpleLayoutPanel();
		panel.add(actuEditionPanel);
		
		updateButton = new Button("Modifier");
		
		//Layout
		panel.setWidgetLeftWidth(cellList, 0, Unit.PCT, 30, Unit.PCT);
		panel.setWidgetLeftWidth(actuEditionPanel, 32, Unit.PCT, 100, Unit.PCT);
	}
	
	public Widget asWidget() {
		return this;
	}

	public HasClickHandlers getUpdateButton() {
		return updateButton;
	}
	
	private void buildActuEditionPanel(ActuUi pActuUi) {
		LayoutPanel lPanel = new LayoutPanel();
		
		//Input
		//Title
		title = new TextBox();
		title.setValue(pActuUi.getTitle());
		InputPanel lTitleInputPanel = new InputPanel("Titre", title);
		
		//Summary
		summary = new TextBox();
		summary.setValue(pActuUi.getSummary());
		InputPanel lSummaryInputPanel = new InputPanel("Description courte", summary);
		
		//Date
		date = new DateBox();
		date.setValue(pActuUi.getCreationDate());
		InputPanel lDateInputPanel = new InputPanel("Date", date);
		
		//Contenu
		content = new RichTextArea();
		content.setHTML(SafeHtmlUtils.fromString(pActuUi.getContent()));
		InputPanel lContentInputPanel = new InputPanel("Actu", content);
		
		lPanel.add(lTitleInputPanel);
		lPanel.add(lSummaryInputPanel);
		lPanel.add(lDateInputPanel);
		lPanel.add(lContentInputPanel);
		lPanel.add(updateButton);
		lPanel.setWidgetTopHeight(lTitleInputPanel, 1, Unit.PCT, 10, Unit.PCT);
		lPanel.setWidgetTopHeight(lSummaryInputPanel, 12, Unit.PCT, 10, Unit.PCT);
		lPanel.setWidgetTopHeight(lDateInputPanel, 32, Unit.PCT, 10, Unit.PCT);
		lPanel.setWidgetTopHeight(lContentInputPanel, 52, Unit.PCT, 10, Unit.PCT);
		lPanel.setWidgetLeftWidth(updateButton, 30, Unit.PCT, 20, Unit.PCT);
		lPanel.setWidgetTopHeight(updateButton, 74, Unit.PCT, 10, Unit.PCT);
		actuEditionPanel.setWidget(lPanel);
	}

	public Long getActu() {
		return selectionModel.getSelectedObject().getId();
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