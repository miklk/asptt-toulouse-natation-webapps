package com.asptttoulousenatation.client.userspace.admin.user.password;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChangePasswordViewImpl extends Composite implements
		ChangePasswordView {
	
	private Panel panel;
	
	private PasswordTextBox oldPassword;
	private PasswordTextBox newPassword;
	private PasswordTextBox newPasswordConfirm;
	
	private Button validButton;
	
	public ChangePasswordViewImpl() {
		panel = new VerticalPanel();
		initWidget(panel);
		
		Label lTitle = new Label("Changer de mot de passe");
		lTitle.setStyleName(CSS.title());
		panel.add(lTitle);
		
		FlexTable lPanel = new FlexTable();
		lPanel.setCellSpacing(6);
		int index = 0;
		
		oldPassword = new PasswordTextBox();
		lPanel.setHTML(index, 0, "Ancien mot de passe");
		lPanel.setWidget(index, 1, oldPassword);
		index++;
		
		newPassword = new PasswordTextBox();
		lPanel.setHTML(index, 0, "Nouveau mot de passe");
		lPanel.setWidget(index, 1, newPassword);
		index++;
		
		newPasswordConfirm = new PasswordTextBox();
		lPanel.setHTML(index, 0, "Confirmer le nouveau mot de passe");
		lPanel.setWidget(index, 1, newPasswordConfirm);
		index++;
		
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		validButton = new Button("Valider");
		lPanel.setWidget(index, 0, validButton);
		
		panel.add(lPanel);
	}

	public HasValue<String> getOldPassword() {
		return oldPassword;
	}

	public HasValue<String> getNewPassword() {
		return newPassword;
	}

	public HasClickHandlers getValidButton() {
		return validButton;
	}
}