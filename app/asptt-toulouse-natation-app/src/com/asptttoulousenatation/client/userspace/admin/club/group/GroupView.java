package com.asptttoulousenatation.client.userspace.admin.club.group;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface GroupView extends IsWidget {

	public HasClickHandlers getCreateButton();
	public HasClickHandlers getUpdateButton();
	public HasClickHandlers getDeleteButton();
	public HasValue<String> getGroupTitle();
	public HasValue<Boolean> getGroupLicenceFfn();
	public HasValue<Boolean> getGroupInscription();
	public Long getGroupId();
	public HasValue<String> getGroupTarif();
	public HasValue<String> getGroupTarif2();
	public HasValue<String> getGroupTarif3();
	public HasValue<String> getGroupTarif4();
	public HasValue<String> getGroupTarifWeight();
	public HasValue<Boolean> getSeanceUnique();
	public HasValue<Boolean> getNouveau();
	public HasValue<String> getDescription();
}