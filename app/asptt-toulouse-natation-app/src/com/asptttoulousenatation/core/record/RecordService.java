package com.asptttoulousenatation.core.record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

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
	
	private static final String[] CATEGORIES = new String[]{"Toutes Catégories", "17 ans (Cadet)", "16 ans (Cadet)", "15 ans (Minime)", "14 ans (Minime)", "13 ans (Benjamin)", "12 ans (Benjamin)"};
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
		NAGE_ORDER.put("Dos", 1);
		NAGE_ORDER.put("Brasse", 2);
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
	
	@Path("/epreuves/{bassin}/{sexe}")
	@GET
	public List<RecordEpreuveEntity> epreuves(@PathParam("bassin") String bassin, @PathParam("sexe") String sexe) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				2);
		criteria.add(new CriterionDao<String>(RecordEpreuveEntityFields.BASSIN,
				bassin, Operator.EQUAL));
		criteria.add(new CriterionDao<String>(RecordEpreuveEntityFields.SEXE,
				sexe, Operator.EQUAL));
		List<RecordEpreuveEntity> epreuves = new ArrayList<>(epreuveDao.find(criteria));
		Collections.sort(epreuves, new Comparator<RecordEpreuveEntity>() {

			@Override
			public int compare(RecordEpreuveEntity o1, RecordEpreuveEntity o2) {
				CompareToBuilder comparator = new CompareToBuilder();
				comparator.append(NAGE_ORDER.get(o1.getNage()), NAGE_ORDER.get(o2.getNage())).append(DISTANCE_ORDER.get(o1.getDistance()), DISTANCE_ORDER.get(o2.getDistance()));
				return comparator.toComparison();
			}
		});
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
	
	@Path("/last-updated")
	@GET
	@Consumes("application/json")
	public Date findLastUpdated() {
		return dao.findMaxUpdated();
	}
	
	@Path("/create/{epreuve}")
	@PUT
	public void insert(@PathParam("epreuve") String epreuves, String texte) {
		String[] epreuveList = epreuves.split(";");
		try (Scanner scanner = new Scanner(texte)) {
			int indexEpreuve = 0;
			while (scanner.hasNextLine()) {
				Long epreuve = Long.parseLong(epreuveList[indexEpreuve]);
				String line1 = scanner.nextLine();
				String line2 = scanner.nextLine();
				String line3 = scanner.nextLine();
				String line4 = scanner.nextLine();
				String line5 = scanner.nextLine();
				String[] lineSplitted1 = line1.split(";");
				String[] lineSplitted2 = line2.split(";");
				String[] lineSplitted3 = line3.split(";");
				String[] lineSplitted4 = line4.split(";");
				String[] lineSplitted5 = line5.split(";");
				for (int i = 1; i < 8; i++) {
					RecordEntity record = new RecordEntity();
					record.setAge(CATEGORIES[i-1]);
					record.setAnnee(Integer.parseInt(lineSplitted2[i]));
					record.setJour(DateTime.parse(lineSplitted4[i], DateTimeFormat.forPattern("MM/dd/yy")).toDate());
					record.setLieu(lineSplitted5[i]);
					String[] nomPrenom = lineSplitted1[i].split(" ");
					record.setNom(nomPrenom[0]);
					if(nomPrenom.length > 1) {
						record.setPrenom(nomPrenom[1]);
					}
					record.setTemps(lineSplitted3[i]);
					RecordEpreuveEntity epreuveEntity = epreuveDao.get(epreuve);
					record.setEpreuve(epreuveEntity.getId());
					dao.save(record);
				}
				indexEpreuve++;
			}
		}
	}
	
	@Path("/update-cat")
	@POST
	public void updateCat() {
		List<RecordEntity> records = dao.getAll();
		for(RecordEntity record : records) {
			if(record.getAge().contains("Toutes")) {
				record.setAge("Toutes Catégories");
				dao.save(record);
			}
		}
	}
}