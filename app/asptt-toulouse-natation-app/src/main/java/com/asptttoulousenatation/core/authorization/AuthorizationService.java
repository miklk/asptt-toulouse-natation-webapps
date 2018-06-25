package com.asptttoulousenatation.core.authorization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;

import com.asptttoulousenatation.core.authentication.TokenManager;
import com.asptttoulousenatation.core.server.dao.entity.user.UserAuthorizationEntity;
import com.asptttoulousenatation.core.server.dao.user.UserAuthorizationDao;

@Path("/authorization")
@Produces("application/json")
public class AuthorizationService {
	
	private UserAuthorizationDao dao = new UserAuthorizationDao();

	@Path("/hasAccess/{token}/{right}")
	@GET
	public HasAccessResult hasAccess(@PathParam("token") String token, @PathParam("right") String right) {
		final boolean hasAccess;
		if(TokenManager.getInstance().contains(token)) {
			List<UserAuthorizationEntity> rights = dao.findByUserAndAccess(TokenManager.getInstance().getUser(token), right);
			hasAccess = CollectionUtils.isNotEmpty(rights);
		} else {
			hasAccess = false;
		}
		
		HasAccessResult result = new HasAccessResult();
		result.setHasAccess(hasAccess);
		return result;
	}
	
	@Path("/access/{token}")
	@GET
	public List<String> findAccess(@PathParam("token") String token) {
		final List<String> access;
		if(TokenManager.getInstance().contains(token)) {
			List<UserAuthorizationEntity> rights = dao.findByUser(TokenManager.getInstance().getUser(token));
			access = new ArrayList<>(rights.size());
			for(UserAuthorizationEntity right: rights) {
				access.add(right.getAccess());
			}
		} else {
			access = Collections.emptyList();
		}
		return access;
	} 
}