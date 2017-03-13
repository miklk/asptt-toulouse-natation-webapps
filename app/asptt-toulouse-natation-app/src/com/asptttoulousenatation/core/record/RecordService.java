package com.asptttoulousenatation.core.record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.builder.CompareToBuilder;

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
	
	private static final Map<String, Integer> DISTANCE_ORDER;
	private static final Map<String, Integer> NAGE_ORDER;
	
	static {
		DISTANCE_ORDER = new HashMap<>();
		DISTANCE_ORDER.put("50", 0);
		DISTANCE_ORDER.put("100", 1);
		DISTANCE_ORDER.put("200", 2);
		DISTANCE_ORDER.put("250", 3);
		DISTANCE_ORDER.put("400", 4);
		DISTANCE_ORDER.put("800", 5);
		DISTANCE_ORDER.put("1500", 6);
		
		NAGE_ORDER = new HashMap<>();
		NAGE_ORDER.put("NL", 0);
		NAGE_ORDER.put("Brasse", 1);
		NAGE_ORDER.put("Dos", 2);
		NAGE_ORDER.put("Papillon", 3);
		NAGE_ORDER.put("4N", 4);
		
	}
	
	
	private RecordDao dao = new RecordDao();
	private RecordEpreuveDao epreuveDao = new RecordEpreuveDao();
	
	@Path("/{bassin}/{sexe}")
	@GET
	@Consumes("application/json")
	public List<RecordUi> find(@PathParam("bassin") String bassin, @PathParam("sexe") String sexe) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(RecordEpreuveEntityFields.BASSIN,
				bassin, Operator.EQUAL));
		criteria.add(new CriterionDao<String>(RecordEpreuveEntityFields.SEXE,
				sexe, Operator.EQUAL));
		List<RecordEpreuveEntity> epreuves = epreuveDao.find(criteria);
		List<RecordUi> records = new ArrayList<>();
		for(RecordEpreuveEntity epreuve: epreuves) {
			RecordUi ui = new RecordUi();
			ui.setEpreuve(epreuve);
			List<RecordEntity> entities = dao.findByEpreuve(epreuve.getId());
			for(RecordEntity entity: entities) {
				ui.addRecord(entity);
			}
			records.add(ui);
		}
		Collections.sort(records, new Comparator<RecordUi>() {

			@Override
			public int compare(RecordUi o1, RecordUi o2) {
				CompareToBuilder comparator = new CompareToBuilder();
				comparator.append(NAGE_ORDER.get(o1.getEpreuve().getNage()), NAGE_ORDER.get(o2.getEpreuve().getNage())).append(DISTANCE_ORDER.get(o1.getEpreuve().getDistance()), DISTANCE_ORDER.get(o2.getEpreuve().getDistance()));
				return comparator.toComparison();
			}
		});
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
					epreuve.setSexe("0");
					epreuveDao.save(epreuve);
				}
				for (String distance : distances[i]) {
					RecordEpreuveEntity epreuve = new RecordEpreuveEntity();
					epreuve.setBassin(bassin);
					epreuve.setDistance(distance);
					epreuve.setNage(nage);
					epreuve.setSexe("1");
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
	
	@Path("/epreuves/{bassin}")
	@GET
	public List<RecordEpreuveEntity> epreuves(@PathParam("bassin") String bassin) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(RecordEpreuveEntityFields.BASSIN,
				bassin, Operator.EQUAL));
		List<RecordEpreuveEntity> epreuves = epreuveDao.find(criteria);
		return epreuves;
	}
	
	@Path("/by-epreuve/{epreuve}/{categorie}")
	@GET
	@Consumes("application/json")
	public RecordUi findByEpreuve(@PathParam("epreuve") Long epreuveId, @PathParam("categorie") String categorie) {
		RecordEpreuveEntity epreuve = epreuveDao.get(epreuveId);
		RecordUi ui = new RecordUi();
		ui.setEpreuve(epreuve);
		List<RecordEntity> entities = dao.findByEpreuveAndAge(epreuve.getId(), categorie);
		ui.setRecords(new ArrayList<>(entities));
		return ui;
	}
}