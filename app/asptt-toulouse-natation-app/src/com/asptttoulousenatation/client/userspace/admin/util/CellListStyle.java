package com.asptttoulousenatation.client.userspace.admin.util;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellList.Resources;
import com.google.gwt.user.cellview.client.CellList.Style;

public class CellListStyle implements Resources {

	public ImageResource cellListSelectedBackground() {
		// TODO Auto-generated method stub
		return null;
	}

	public Style cellListStyle() {
		return new Style() {

			public boolean ensureInjected() {
				return false;
			}

			public String getText() {
				return "";
			}

			public String getName() {
				return "";
			}

			public String cellListKeyboardSelectedItem() {
				return CSS.userSpaceContentListSelected();
			}

			public String cellListOddItem() {
				return CSS.userSpaceContentListOdd();
			}

			public String cellListSelectedItem() {
				return CSS.userSpaceContentListSelected();
			}

			public String cellListWidget() {
				return CSS.userSpaceContentList();
			}

			public String cellListEvenItem() {
				return "";
			}
		};
	}
}