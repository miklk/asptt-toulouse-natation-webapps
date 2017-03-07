package com.asptttoulousenatation.core.record;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.joda.time.DateTime;

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
		dao.save(entity);
	}
	
	@Path("/init-epreuves")
	@PUT
	public void initEpreuve() {
		for(RecordEpreuveEntity entity: epreuveDao.getAll()) {
			epreuveDao.delete(entity);
		}
		String[][] distances = { { "50", "100", "200", "400", "800", "1500" }, { "50", "100", "200" },
				{ "50", "100", "200" }, { "50", "100", "200" }, { "100", "200", "400" }, };
		String[] nages = { "NL", "Dos", "Brasse", "Papillon", "4N" };
		String[] bassins = { "25", "50" };
		for (String bassin : bassins) {
			for (int i = 0; i < nages.length; i++) {
				String nage = nages[i];
				for (String distance : distances[i]) {
					RecordEpreuveEntity epreuve = new RecordEpreuveEntity();
					epreuve.setBassin(bassin);
					epreuve.setDistance(distance);
					epreuve.setNage(nage);
					epreuveDao.save(epreuve);
				}
			}
		}
	}
	
	@Path("/init-records")
	@PUT
	public void initRecord() {
		for(RecordEntity entity: dao.getAll()) {
			dao.delete(entity);
		}
		String[] ages = { "12", "13", "14", "15", "16", "17", "18" };
		List<RecordEpreuveEntity> epreuves = epreuveDao.getAll();
		for (RecordEpreuveEntity epreuve : epreuves) {
			for (String age : ages) {
				RecordEntity record = new RecordEntity();
				record.setAge(age);
				record.setEpreuve(epreuve.getId());
				dao.save(record);
			}
		}
	}
}
