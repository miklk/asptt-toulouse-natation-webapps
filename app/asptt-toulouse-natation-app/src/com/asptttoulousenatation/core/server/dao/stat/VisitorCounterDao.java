package com.asptttoulousenatation.core.server.dao.stat;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.stat.VisitorCounter;

public class VisitorCounterDao extends DaoBase<VisitorCounter> {

	@Override
	public Class<VisitorCounter> getEntityClass() {
		return VisitorCounter.class;
	}

	@Override
	public String getAlias() {
		return "visitorCounter";
	}
	
	@Override
	public Object getKey(VisitorCounter pEntity) {
		return pEntity.getId();
	}

}
