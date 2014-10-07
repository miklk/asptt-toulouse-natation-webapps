package com.asptttoulousenatation.client.userspace.admin.structure.menu.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.TreeViewModel;

public class MenuTreeViewModel implements TreeViewModel {

	private final ListDataProvider<MenuUi> menuProvider;
	private final SelectionModel<MenuUi> selectionModel;
	
	
	public MenuTreeViewModel(SelectionModel<MenuUi> pSelectionModel, List<MenuUi> pMenuList) {
		super();
		selectionModel = pSelectionModel;
		Collections.sort(pMenuList, new Comparator<MenuUi>() {

			@Override
			public int compare(MenuUi pO1, MenuUi pO2) {
				return pO1.getOrder() > pO2.getOrder() ? -1: 1;
			}
			
		});
		menuProvider = new ListDataProvider<MenuUi>(pMenuList);
	}

	public <T> NodeInfo<?> getNodeInfo(T pValue) {
		final NodeInfo<?> result;
		if(pValue == null) {//Its top
			result = new DefaultNodeInfo<MenuUi>(menuProvider, new MenuParentCell(getCells()), null, null);
		} else if(pValue instanceof MenuUi) {
			MenuUi lParentMenu = (MenuUi) pValue;
			result = new DefaultNodeInfo<MenuUi>(new ListDataProvider<MenuUi>(lParentMenu.getSubMenus()), new MenuCell(), selectionModel, null);
		} else {
			throw new IllegalArgumentException("Error unsupported element");
		}
		return result;
	}

	public boolean isLeaf(Object pValue) {
		final boolean result;
		if(pValue instanceof MenuUi) {
			MenuUi lMenu = (MenuUi) pValue;
			result = CollectionUtils.isEmpty(lMenu.getSubMenus());
		} else {
			result = false;
		}
		return result;
	}

	private List<HasCell<MenuUi, ?>> getCells() {
		List<HasCell<MenuUi, ?>> lCells = new ArrayList<HasCell<MenuUi,?>>(2);
		lCells.add(new HasCell<MenuUi, MenuUi>() {
			private MenuEditionCell cell = new MenuEditionCell(new SafeHtmlBuilder().appendEscaped("Edit").toSafeHtml(), new ActionCell.Delegate<MenuUi>() {
				public void execute(MenuUi pObject) {
					selectionModel.setSelected(pObject, true);
				}
			});

			public Cell<MenuUi> getCell() {
				return cell;
			}
			public FieldUpdater<MenuUi, MenuUi> getFieldUpdater() {
				return null;
			}
			public MenuUi getValue(MenuUi pObject) {
				return pObject;
			}
			
		});
		
		lCells.add(new HasCell<MenuUi, MenuUi>() {
			private MenuCell cell = new MenuCell();
			public Cell<MenuUi> getCell() {
				return cell;
			}
			public FieldUpdater<MenuUi, MenuUi> getFieldUpdater() {
				return null;
			}
			public MenuUi getValue(MenuUi pObject) {
				return pObject;
			}
		});
		return lCells;
	}
}
