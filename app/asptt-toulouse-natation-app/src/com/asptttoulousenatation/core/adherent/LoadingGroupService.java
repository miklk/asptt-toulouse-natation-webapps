package com.asptttoulousenatation.core.adherent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.BooleanUtils;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.GroupEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;

@Path("/groupes")
@Produces("application/json")
public class LoadingGroupService {

	private static final Logger LOG = Logger.getLogger(LoadingGroupService.class
			.getName());

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private GroupDao dao = new GroupDao();

	@GET
	public LoadingGroupesUi getGroupes(@QueryParam("nouveau") Boolean nouveau) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Boolean>(GroupEntityFields.INSCRIPTION, Boolean.TRUE,
				Operator.EQUAL));
		if(BooleanUtils.isTrue(nouveau)) {
			criteria.add(new CriterionDao<Boolean>(GroupEntityFields.NOUVEAU, Boolean.TRUE,
					Operator.EQUAL));
		}
		List<GroupEntity> lEntities = dao.find(criteria);
		List<GroupUi> lUis = new GroupTransformer().toUi(lEntities);
		Collections.sort(lUis, new Comparator<GroupUi>() {

			public int compare(GroupUi pO1, GroupUi pO2) {
				return pO1.getTitle().compareTo(pO2.getTitle());
			}
		});
		
		LoadingGroupesUi result = new LoadingGroupesUi();
		result.setGroups(lUis);
		return result;
	}
	
	@Path("/all")
	@GET
	public LoadingGroupesUi getGroupes() {
		List<GroupEntity> lEntities = dao.getAll();
		List<GroupUi> lUis = new GroupTransformer().toUi(lEntities);
		Collections.sort(lUis, new Comparator<GroupUi>() {

			public int compare(GroupUi pO1, GroupUi pO2) {
				return pO1.getTitle().compareTo(pO2.getTitle());
			}
		});
		
		LoadingGroupesUi result = new LoadingGroupesUi();
		result.setGroups(lUis);
		return result;
	}
}