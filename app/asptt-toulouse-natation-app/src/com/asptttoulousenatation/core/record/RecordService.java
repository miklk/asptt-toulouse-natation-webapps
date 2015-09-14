package com.asptttoulousenatation.core.record;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
	public List<RecordEntity> find(@PathParam("bassin") String bassin) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(RecordEpreuveEntityFields.BASSIN,
				bassin, Operator.EQUAL));
		List<RecordEpreuveEntity> epreuves = epreuveDao.find(criteria);
		return null;
	}
}
