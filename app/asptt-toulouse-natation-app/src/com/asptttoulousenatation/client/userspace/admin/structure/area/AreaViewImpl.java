package com.asptttoulousenatation.client.userspace.admin.structure.area;

import java.util.List;

import com.asptttoulousenatation.client.userspace.admin.structure.menu.ui.MenuCell;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentUI;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RichTextToolbar;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class AreaViewImpl extends ResizeComposite implements AreaView {

	private List<MenuUi> data;
	private LayoutPanel panel;

	private CellList<MenuUi> cellList;
	private SingleSelectionModel<MenuUi> selectionModel;
	private SimpleLayoutPanel editionPanel;

	private TextBox menuTitleInput;
	private TextBox summaryInput;
	private RichTextArea contentInput;
	private Button updateButton;

	public AreaViewImpl(List<MenuUi> pData) {
		data = pData;
		panel = new LayoutPanel();
		initWidget(panel);

		cellList = new CellList<MenuUi>(new MenuCell());
		cellList.setRowData(data);
		panel.add(cellList);
		selectionModel = new SingleSelectionModel<MenuUi>();
		cellList.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

					public void onSelectionChange(SelectionChangeEvent pEvent) {
						buildEditionPanel(selectionModel.getSelectedObject());

					}
				});

		editionPanel = new SimpleLayoutPanel();
		panel.add(editionPanel);

		updateButton = new Button("Modifier");

		// Layout
		panel.setWidgetLeftWidth(cellList, 0, Unit.PCT, 20, Unit.PCT);
		panel.setWidgetLeftWidth(editionPanel, 22, Unit.PCT, 100, Unit.PCT);
	}

	private void buildEditionPanel(MenuUi pMenuUi) {
		FlexTable lPanel = new FlexTable();
		
		//Menu title
		menuTitleInput = new TextBox();
		menuTitleInput.setWidth("300px");
		menuTitleInput.setValue(pMenuUi.getTitle());
		lPanel.setHTML(0, 0, "Intitulé du menu");
		lPanel.setWidget(0, 1, menuTitleInput);
		
		//Content edition
		ContentUI lContentUI = pMenuUi.getContentSet().get(0);
		// Summary
		summaryInput = new TextBox();
		summaryInput.setWidth("300px");
		summaryInput.setValue(lContentUI.getSummary());
		lPanel.setHTML(1, 0, "Résumé");
		lPanel.setWidget(1, 1, summaryInput);

		// Content
		contentInput = new RichTextArea();
		contentInput.setSize("100%", "14em");
		RichTextToolbar lRichTextToolbar = new RichTextToolbar(contentInput);
	    lRichTextToolbar.setWidth("100%");
		Grid lRichText = new Grid(2, 1);
		lRichText.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		lRichText.getElement().getStyle().setBorderColor("#CCCCCC");
		lRichText.getElement().getStyle().setBorderWidth(1, Unit.PX);
		lRichText.setWidget(0, 0, lRichTextToolbar);
		lRichText.setWidget(1, 0, contentInput);
		lPanel.setHTML(2, 0, "Contenu");
		lPanel.setWidget(2, 1, lRichText);

		contentInput.setHTML(SafeHtmlUtils.fromString(new String(lContentUI.getData())));
		
		lPanel.setWidget(3, 0, updateButton);
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lCellFormatter.setColSpan(3, 0, 2);
		lCellFormatter.setHorizontalAlignment(3, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		
		editionPanel.setWidget(lPanel);
	}

	
	public Long getContentId() {
		return selectionModel.getSelectedObject().getContentSet().get(0).getId();
	}

	public HasValue<String> getSummary() {
		return summaryInput;
	}

	public String getContent() {
		return contentInput.getHTML();
	}

	public HasClickHandlers getUpdateButton() {
		return updateButton;
	}

	public HasValue<String> getMenuTitle() {
		return menuTitleInput;
	}
}