package com.asptt.core.piscine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.asptt.core.server.dao.club.group.PiscineDao;
import com.asptt.core.server.dao.club.group.SlotDao;
import com.asptt.core.server.dao.entity.club.group.PiscineEntity;
import com.asptt.core.server.dao.entity.club.group.SlotEntity;

@Path("/piscines")
@Produces("application/json")
public class PiscineService {

	private SlotDao slotDao = new SlotDao();
	private PiscineDao dao = new PiscineDao();

	@GET
	public PiscineListResult list() {
		PiscineListResult result = new PiscineListResult();
		Map<String, PiscineListResultBean> piscines = new HashMap<>();
		List<SlotEntity> entities = slotDao.getAll();
		for (SlotEntity entity : entities) {
			PiscineListResultBean bean;
			if (piscines.containsKey(entity.getSwimmingPool())) {
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

	@Path("/all")
	@GET
	public List<PiscineEntity> findAll() {
		List<PiscineEntity> entities = new ArrayList<PiscineEntity>(dao.getAll());
		Collections.sort(entities, new Comparator<PiscineEntity>() {

			@Override
			public int compare(PiscineEntity o1, PiscineEntity o2) {
				return o1.getIntitule().compareTo(o2.getIntitule());
			}
		});
		return entities;
	}
}
