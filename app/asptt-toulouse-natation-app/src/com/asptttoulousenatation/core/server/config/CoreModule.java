package com.asptttoulousenatation.core.server.config;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.asptttoulousenatation.core.server.club.group.CreateGroupActionHandler;
import com.asptttoulousenatation.core.server.club.group.GetAllGroupActionHandler;
import com.asptttoulousenatation.core.server.club.group.UpdateGroupActionHandler;
import com.asptttoulousenatation.core.server.club.slot.CreateSlotActionHandler;
import com.asptttoulousenatation.core.server.club.slot.GetAllSlotActionHandler;
import com.asptttoulousenatation.core.server.club.slot.UpdateSlotActionHandler;
import com.asptttoulousenatation.core.server.competition.CreateCompetitionActionHandler;
import com.asptttoulousenatation.core.server.competition.GetAllCompetitionActionHandler;
import com.asptttoulousenatation.core.server.competition.OfficielDayActionHandler;
import com.asptttoulousenatation.core.server.competition.UpdateCompetitionActionHandler;
import com.asptttoulousenatation.core.server.document.DeleteDocumentActionHandler;
import com.asptttoulousenatation.core.server.document.GetDocumentActionHandler;
import com.asptttoulousenatation.core.server.document.UpdateDocumentActionHandler;
import com.asptttoulousenatation.core.server.user.AuthenticationActionHandler;
import com.asptttoulousenatation.core.server.user.IsAuthenticatedActionHandler;
import com.asptttoulousenatation.core.server.user.LogoutActionHandler;
import com.asptttoulousenatation.core.server.user.PasswordForgetActionHandler;
import com.asptttoulousenatation.core.shared.actu.GetAllActuAction;
import com.asptttoulousenatation.core.shared.club.group.CreateGroupAction;
import com.asptttoulousenatation.core.shared.club.group.GetAllGroupAction;
import com.asptttoulousenatation.core.shared.club.group.UpdateGroupAction;
import com.asptttoulousenatation.core.shared.club.slot.CreateSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.GetAllSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.UpdateSlotAction;
import com.asptttoulousenatation.core.shared.competition.CreateCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.GetAllCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.OfficielDayAction;
import com.asptttoulousenatation.core.shared.competition.UpdateCompetitionAction;
import com.asptttoulousenatation.core.shared.document.DeleteDocumentAction;
import com.asptttoulousenatation.core.shared.document.GetDocumentAction;
import com.asptttoulousenatation.core.shared.document.UpdateDocumentAction;
import com.asptttoulousenatation.core.shared.structure.LoadContentAction;
import com.asptttoulousenatation.core.shared.user.AuthenticationAction;
import com.asptttoulousenatation.core.shared.user.IsAuthenticatedAction;
import com.asptttoulousenatation.core.shared.user.LogoutAction;
import com.asptttoulousenatation.core.shared.user.PasswordForgetAction;
import com.asptttoulousenatation.server.init.InitActionHandler;
import com.asptttoulousenatation.server.init.InitUserSpaceActionHandler;
import com.asptttoulousenatation.server.init.LoadContentActionHandler;
import com.asptttoulousenatation.server.userspace.admin.actu.GetAllActuActionHandler;
import com.asptttoulousenatation.server.userspace.admin.actu.PublishActuActionHandler;
import com.asptttoulousenatation.server.userspace.admin.actu.UpdateActuActionHandler;
import com.asptttoulousenatation.server.userspace.admin.structure.GetAreaActionHandler;
import com.asptttoulousenatation.server.userspace.admin.structure.content.CreateContentActionHandler;
import com.asptttoulousenatation.server.userspace.admin.structure.content.UpdateContentActionHandler;
import com.asptttoulousenatation.server.userspace.admin.user.CreateUserActionHandler;
import com.asptttoulousenatation.server.userspace.admin.user.DeleteUserActionHandler;
import com.asptttoulousenatation.server.userspace.admin.user.GetAllUserActionHandler;
import com.asptttoulousenatation.server.userspace.admin.user.UpdateUserActionHandler;
import com.asptttoulousenatation.shared.init.InitAction;
import com.asptttoulousenatation.shared.init.InitUserSpaceAction;
import com.asptttoulousenatation.shared.userspace.admin.actu.PublishActuAction;
import com.asptttoulousenatation.shared.userspace.admin.actu.UpdateActuAction;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.GetAreaAction;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.CreateContentAction;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.UpdateContentAction;
import com.asptttoulousenatation.shared.userspace.admin.user.CreateUserAction;
import com.asptttoulousenatation.shared.userspace.admin.user.DeleteUserAction;
import com.asptttoulousenatation.shared.userspace.admin.user.GetAllUserAction;
import com.asptttoulousenatation.shared.userspace.admin.user.UpdateUserAction;

public class CoreModule extends ActionHandlerModule {
	
	

	@Override
	protected void configureHandlers() {
		bindHandler(GetAllActuAction.class, GetAllActuActionHandler.class);
		bindHandler(PublishActuAction.class, PublishActuActionHandler.class);
		bindHandler(GetAreaAction.class, GetAreaActionHandler.class);
		bindHandler(CreateContentAction.class, CreateContentActionHandler.class);
		bindHandler(UpdateContentAction.class, UpdateContentActionHandler.class);
		bindHandler(UpdateActuAction.class, UpdateActuActionHandler.class);
		bindHandler(GetAllUserAction.class, GetAllUserActionHandler.class);
		bindHandler(CreateUserAction.class, CreateUserActionHandler.class);
		bindHandler(UpdateUserAction.class, UpdateUserActionHandler.class);
		bindHandler(DeleteUserAction.class, DeleteUserActionHandler.class);
		
		bindHandler(AuthenticationAction.class, AuthenticationActionHandler.class);
		bindHandler(IsAuthenticatedAction.class, IsAuthenticatedActionHandler.class);
		bindHandler(LogoutAction.class, LogoutActionHandler.class);
		bindHandler(PasswordForgetAction.class, PasswordForgetActionHandler.class);
		
		bindHandler(InitAction.class, InitActionHandler.class);
		bindHandler(InitUserSpaceAction.class, InitUserSpaceActionHandler.class);
		
		//Group
		bindHandler(GetAllGroupAction.class, GetAllGroupActionHandler.class);
		bindHandler(CreateGroupAction.class, CreateGroupActionHandler.class);
		bindHandler(UpdateGroupAction.class, UpdateGroupActionHandler.class);
		
		//Slot
		bindHandler(GetAllSlotAction.class, GetAllSlotActionHandler.class);
		bindHandler(CreateSlotAction.class, CreateSlotActionHandler.class);
		bindHandler(UpdateSlotAction.class, UpdateSlotActionHandler.class);
		
		//Competition
		bindHandler(CreateCompetitionAction.class, CreateCompetitionActionHandler.class);
		bindHandler(UpdateCompetitionAction.class, UpdateCompetitionActionHandler.class);
		bindHandler(GetAllCompetitionAction.class, GetAllCompetitionActionHandler.class);
		bindHandler(OfficielDayAction.class, OfficielDayActionHandler.class);
		
		bindHandler(LoadContentAction.class, LoadContentActionHandler.class);
		
		//Document
		bindHandler(GetDocumentAction. class, GetDocumentActionHandler.class);
		bindHandler(UpdateDocumentAction. class, UpdateDocumentActionHandler.class);
		bindHandler(DeleteDocumentAction. class, DeleteDocumentActionHandler.class);
	}
}