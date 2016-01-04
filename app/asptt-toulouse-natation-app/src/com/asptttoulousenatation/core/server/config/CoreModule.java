
package com.asptttoulousenatation.core.server.config;

import com.asptttoulousenatation.core.server.document.DeleteDocumentActionHandler;
import com.asptttoulousenatation.core.server.document.GetDocumentActionHandler;
import com.asptttoulousenatation.core.server.document.UpdateDocumentActionHandler;
import com.asptttoulousenatation.core.server.reference.IsDataUpdateActionHandler;
import com.asptttoulousenatation.core.server.reference.SetDataUpdateActionHandler;
import com.asptttoulousenatation.core.server.structure.area.CreateAreaActionHandler;
import com.asptttoulousenatation.core.server.structure.area.DeleteAreaActionHandler;
import com.asptttoulousenatation.core.server.structure.area.UpdateAreaActionHandler;
import com.asptttoulousenatation.core.server.structure.menu.CreateMenuActionHandler;
import com.asptttoulousenatation.core.server.structure.menu.DeleteMenuActionHandler;
import com.asptttoulousenatation.core.server.structure.menu.GetMenuActionHandler;
import com.asptttoulousenatation.core.server.structure.menu.UpdateMenuActionHandler;
import com.asptttoulousenatation.core.server.user.AuthenticationActionHandler;
import com.asptttoulousenatation.core.server.user.ChangePasswordActionHandler;
import com.asptttoulousenatation.core.server.user.IsAuthenticatedActionHandler;
import com.asptttoulousenatation.core.server.user.LogoutActionHandler;
import com.asptttoulousenatation.core.server.user.PasswordForgetActionHandler;
import com.asptttoulousenatation.core.shared.document.DeleteDocumentAction;
import com.asptttoulousenatation.core.shared.document.GetDocumentAction;
import com.asptttoulousenatation.core.shared.document.UpdateDocumentAction;
import com.asptttoulousenatation.core.shared.reference.IsDataUpdateAction;
import com.asptttoulousenatation.core.shared.reference.SetDataUpdateAction;
import com.asptttoulousenatation.core.shared.structure.LoadContentAction;
import com.asptttoulousenatation.core.shared.structure.area.CreateAreaAction;
import com.asptttoulousenatation.core.shared.structure.area.DeleteAreaAction;
import com.asptttoulousenatation.core.shared.structure.area.UpdateAreaAction;
import com.asptttoulousenatation.core.shared.structure.menu.CreateMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.DeleteMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.GetMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.UpdateMenuAction;
import com.asptttoulousenatation.core.shared.user.AuthenticationAction;
import com.asptttoulousenatation.core.shared.user.ChangePasswordAction;
import com.asptttoulousenatation.core.shared.user.IsAuthenticatedAction;
import com.asptttoulousenatation.core.shared.user.LogoutAction;
import com.asptttoulousenatation.core.shared.user.PasswordForgetAction;
import com.asptttoulousenatation.server.init.InitActionHandler;
import com.asptttoulousenatation.server.init.InitUserSpaceActionHandler;
import com.asptttoulousenatation.server.init.LoadContentActionHandler;
import com.asptttoulousenatation.server.userspace.admin.structure.content.CreateContentActionHandler;
import com.asptttoulousenatation.server.userspace.admin.structure.content.UpdateContentActionHandler;
import com.asptttoulousenatation.shared.init.InitAction;
import com.asptttoulousenatation.shared.init.InitUserSpaceAction;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.CreateContentAction;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.UpdateContentAction;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

public class CoreModule extends ActionHandlerModule {
	
	

	@Override
	protected void configureHandlers() {
		bindHandler(CreateContentAction.class, CreateContentActionHandler.class);
		bindHandler(UpdateContentAction.class, UpdateContentActionHandler.class);
		
		bindHandler(AuthenticationAction.class, AuthenticationActionHandler.class);
		bindHandler(IsAuthenticatedAction.class, IsAuthenticatedActionHandler.class);
		bindHandler(LogoutAction.class, LogoutActionHandler.class);
		bindHandler(PasswordForgetAction.class, PasswordForgetActionHandler.class);
		bindHandler(ChangePasswordAction.class, ChangePasswordActionHandler.class);
		
		bindHandler(InitAction.class, InitActionHandler.class);
		bindHandler(InitUserSpaceAction.class, InitUserSpaceActionHandler.class);
		
		
		
		bindHandler(LoadContentAction.class, LoadContentActionHandler.class);
		
		//Document
		bindHandler(GetDocumentAction. class, GetDocumentActionHandler.class);
		bindHandler(UpdateDocumentAction. class, UpdateDocumentActionHandler.class);
		bindHandler(DeleteDocumentAction. class, DeleteDocumentActionHandler.class);
		
		//Area
		bindHandler(CreateAreaAction.class, CreateAreaActionHandler.class);
		bindHandler(UpdateAreaAction.class, UpdateAreaActionHandler.class);
		bindHandler(DeleteAreaAction.class, DeleteAreaActionHandler.class);
		//Menu
		bindHandler(CreateMenuAction.class, CreateMenuActionHandler.class);
		bindHandler(UpdateMenuAction.class, UpdateMenuActionHandler.class);
		bindHandler(DeleteMenuAction.class, DeleteMenuActionHandler.class);
		bindHandler(GetMenuAction.class, GetMenuActionHandler.class);
		
		//Data update
		bindHandler(SetDataUpdateAction.class, SetDataUpdateActionHandler.class);
		bindHandler(IsDataUpdateAction.class, IsDataUpdateActionHandler.class);
	}
}