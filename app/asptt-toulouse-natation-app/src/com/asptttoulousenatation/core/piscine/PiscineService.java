package com.asptttoulousenatation.core.piscine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;

@Path("/piscines")
@Produces("application/json")
public class PiscineService {

	private SlotDao dao = new SlotDao();
	
	@GET
	public PiscineListResult list() {
		PiscineListResult result = new PiscineListResult();
		Map<String, PiscineListResultBean> piscines = new HashMap<>();
		List<SlotEntity> entities = dao.getAll();
		for(SlotEntity entity: entities) {
			PiscineListResultBean bean;
			if(piscines.containsKey(entity.getSwimmingPool())) {
				bean = piscines.get(entity.getSwimmingPool());
			} else {
				bean = new PiscineListResultBean();
				bean.setIntitule(entity.getSwimmingPool());
				piscines.put(entity.getSwimmingPool(), bean);
			}
			bean.addCreneau(entity.getId());
		}
		result.addPiscines(piscines.values());
		return result;
	}
}
