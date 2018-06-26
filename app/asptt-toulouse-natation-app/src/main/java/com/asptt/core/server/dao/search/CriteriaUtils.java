package com.asptt.core.server.dao.search;

import java.util.List;

public class CriteriaUtils {

	public static String getWhereClause(
			List<CriterionDao<? extends Object>> pCriteria, Operator pOperator, String pAlias) {
		final StringBuilder lBuilder = new StringBuilder();
		for (int i = 0; i < pCriteria.size(); i++) {
			CriterionDao<? extends Object> lCriterion = pCriteria.get(i);
			lBuilder.append(lCriterion.asWhereClause(pAlias));
			if (i < pCriteria.size() - 1) {
				lBuilder.append(" ").append(pOperator.toString()).append(" ");
			}
		}
		return lBuilder.toString();
	}
}