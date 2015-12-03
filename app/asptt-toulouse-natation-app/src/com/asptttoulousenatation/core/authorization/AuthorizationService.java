package com.asptttoulousenatation.core.authorization;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;

import com.asptttoulousenatation.core.authentication.AuthenticationService;
import com.asptttoulousenatation.core.server.dao.entity.user.UserAuthorizationEntity;
import com.asptttoulousenatation.core.server.dao.user.UserAuthorizationDao;

@Path("/authorization")
@Produces("application/json")
public class AuthorizationService {
	
	private UserAuthorizationDao dao = new UserAuthorizationDao();

	@Path("/hasAccess/{token}/{right}")
	@GET
	public HasAccessResult hasAccess(@PathParam("token") Integer token, @PathParam("right") String right) {
		final boolean hasAccess;
		if(AuthenticationService.tokens.containsKey(token)) {
			List<UserAuthorizationEntity> rights = dao.findByUserAndAccess(AuthenticationService.tokens.get(token), right);
			hasAccess = CollectionUtils.isNotEmpty(rights);
		} else {
			hasAccess = false;
		}
		
		HasAccessResult result = new HasAccessResult();
		result.setHasAccess(hasAccess);
		return result;
	}
}