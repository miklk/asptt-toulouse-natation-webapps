package com.asptttoulousenatation.core.user;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.entity.field.UserDataEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.UserEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.server.dao.user.UserDataDao;
import com.asptttoulousenatation.core.server.entity.UserDataTransformer;
import com.asptttoulousenatation.core.server.entity.UserTransformer;
import com.asptttoulousenatation.core.shared.user.UserUi;

@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserService {

	private UserDao userDao = new UserDao();
	private UserDataDao userDataDao = new UserDataDao();
	private UserTransformer userTransformer = new UserTransformer();
	private UserDataTransformer userDataTransformer = new UserDataTransformer();
	
	@GET
	public UserFindResult find(@QueryParam("search") String pSearch) {
		UserFindResult result = new UserFindResult();
		List<UserEntity> userEntities = new ArrayList<>();
		List<UserDataEntity> userDataEntities = new ArrayList<>();
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		if(StringUtils.isBlank(pSearch)) {
			userEntities.addAll(userDao.getAll());
		} else if (StringUtils.contains(pSearch, "@")) {// Email
		criteria.add(new CriterionDao<String>(
				UserEntityFields.EMAILADDRESS, pSearch,
				Operator.EQUAL));
		userEntities.addAll(userDao.find(criteria));
		} else {
			criteria.add(new CriterionDao<String>(
					UserDataEntityFields.LASTNAME, pSearch,
					Operator.EQUAL));
			userDataEntities.addAll(userDataDao.find(criteria));
			if(CollectionUtils.isEmpty(userDataEntities)) {
				criteria.clear();
				criteria.add(new CriterionDao<String>(
						UserDataEntityFields.FIRSTNAME, pSearch,
						Operator.EQUAL));
				userDataEntities.addAll(userDataDao.find(criteria));	
			}
			for(UserDataEntity userData: userDataEntities) {
				List<CriterionDao<? extends Object>> userCriteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				userCriteria.add(new CriterionDao<Long>(
						UserEntityFields.DATA, userData.getId(),
						Operator.EQUAL));
				List<UserEntity> usersEntities = userDao.find(userCriteria);
				if(CollectionUtils.isNotEmpty(usersEntities)) {
					UserUi user = userTransformer.toUi(usersEntities.get(0));
					user.setUserData(userDataTransformer.toUi(userData));
					result.addUser(user);
				}
			}
		}
		if(CollectionUtils.isNotEmpty(userEntities)) {
			for(UserEntity user: userEntities) {
				UserDataEntity userData = userDataDao.get(user.getUserData());
				UserUi userUi = userTransformer.toUi(user);
				userUi.setUserData(userDataTransformer.toUi(userData));
				result.addUser(userUi);
			}
		}
		
		return result;
	}
	
	@Path("/create")
	@POST
	public UserCreateResult create(@QueryParam("userCreateAction") UserCreateAction pAction) {
		UserCreateResult result = new UserCreateResult();
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(
				UserEntityFields.EMAILADDRESS, pAction.getEmail(),
				Operator.EQUAL));
		List<UserEntity> users = userDao.find(criteria);
		if(CollectionUtils.isEmpty(users)) {
			UserEntity userEntity = new UserEntity();
			userEntity.setEmailaddress(pAction.getEmail());
			userEntity.setValidated(true);
			userEntity.setProfiles(pAction.getProfiles());
			UserDataEntity userDataEntity = new UserDataEntity();
			userDataEntity.setFirstName(pAction.getPrenom());
			userDataEntity.setLastName(pAction.getNom());
			UserDataEntity userDataEntityCreated = userDataDao.save(userDataEntity);
			userEntity.setUserData(userDataEntityCreated.getId());
			userDao.save(userEntity);
			result.setSuccess(true);
		} else {
			result.setExists(true);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Path("{user}")
	@DELETE
	public void remove(@PathParam("user") Long userId) {
		UserEntity user = userDao.get(userId);
		userDataDao.delete(user.getUserData());
		userDao.delete(userId);
	}
}