package com.asptt.core.reference;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.asptt.core.server.dao.entity.reference.ParamEntity;
import com.asptt.core.server.dao.reference.ParamDao;

@Path("/params")
public class ParamService {

	private ParamDao paramDao = new ParamDao();
	
	@Path("groupes/{groupe}")
	@GET
	@Consumes("application/json")
	public List<ParamEntity> groupes(@PathParam("groupe") String groupe) {
		return new ArrayList<>(paramDao.findByGroupe(groupe));
	}
	
	@POST
	@Consumes("application/json")
	public void groupes(ParamEntity param) {
		ParamEntity newParam;
		if(param.getId() != null) {
			newParam = paramDao.get(param.getId());
			newParam.setValue(param.getValue());
		} else {
			newParam = param;
		}
		paramDao.save(newParam);
	}
}
