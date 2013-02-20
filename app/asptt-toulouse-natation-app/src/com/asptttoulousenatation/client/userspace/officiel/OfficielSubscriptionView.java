package com.asptttoulousenatation.client.userspace.officiel;

import java.util.List;

import com.asptttoulousenatation.core.shared.competition.CompetitionUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.IsWidget;

public interface OfficielSubscriptionView extends IsWidget {

	public void setData(UserUi pUser, List<CompetitionUi> pCompetitions);
	public void setSubscriptionCommand(Command pSubscriptionCommand);
	public void setUnsubscriptionCommand(Command pUnsubscriptionCommand);
	
	public HasClickHandlers addOfficielButton();
	public HasClickHandlers removeOfficielButton();
	
	public Long getCompetitionId();
	public Long getCompetitionDayId();
}