package com.asptttoulousenatation.core.record;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.asptttoulousenatation.core.server.dao.entity.field.RecordEpreuveEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.record.RecordEntity;
import com.asptttoulousenatation.core.server.dao.entity.record.RecordEpreuveEntity;
import com.asptttoulousenatation.core.server.dao.record.RecordDao;
import com.asptttoulousenatation.core.server.dao.record.RecordEpreuveDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

@Path("/records")
@Produces("application/json")
public class RecordService {

	private static final Logger LOG = Logger.getLogger(RecordService.class
			.getName());
	
	private RecordDao dao = new RecordDao();
	private RecordEpreuveDao epreuveDao = new RecordEpreuveDao();
	
	@Path("/{bassin}")
	@GET
	@Consumes("application/json")
	public List<RecordUi> find(@PathParam("bassin") String bassin) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(RecordEpreuveEntityFields.BASSIN,
				bassin, Operator.EQUAL));
		List<RecordEpreuveEntity> epreuves = epreuveDao.find(criteria);
		List<RecordUi> records = new ArrayList<>();
		for(RecordEpreuveEntity epreuve: epreuves) {
			RecordUi ui = new RecordUi();
			ui.setEpreuve(epreuve);
			List<RecordEntity> entities = dao.findByEpreuve(epreuve.getId());
			ui.setRecords(new ArrayList<>(entities));
			records.add(ui);
		}
		return records;
	}
	
	@Path("/epreuve")
	@POST
	public void createEpreuve(RecordEpreuveEntity epreuve) {
		final RecordEpreuveEntity entity;
		if(epreuve == null) {
			entity = epreuve;
		} else {
			entity = epreuveDao.get(epreuve.getId());
			entity.setBassin(epreuve.getBassin());
			entity.setDistance(epreuve.getDistance());
			entity.setNage(epreuve.getNage());
		}
		epreuveDao.save(entity);
	}
	
	@Path("/record")
	@POST
	public void createRecord(RecordEntity entity) {
		
	}
}
