package com.asptttoulousenatation.core.server.club.subscription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.club.subscription.SubscriptionPriceDao;
import com.asptttoulousenatation.core.server.dao.entity.club.subscription.SubscriptionPrice;
import com.asptttoulousenatation.core.server.dao.entity.field.SubscriptionPriceEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.club.subscription.GetPriceAction;
import com.asptttoulousenatation.core.shared.club.subscription.GetPriceResult;
import com.asptttoulousenatation.core.shared.club.subscription.SubscriptionPriceUi;
import com.asptttoulousenatation.server.userspace.admin.entity.SubscriptionPriceTransformer;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class GetPriceActionHandler implements
		ActionHandler<GetPriceAction, GetPriceResult> {

	private SubscriptionPriceDao dao = new SubscriptionPriceDao();
	private SubscriptionPriceTransformer transformer = new SubscriptionPriceTransformer();
	
	public GetPriceResult execute(GetPriceAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Criteria over group and order
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(2);
		lCriteria.add(new CriterionDao<Object>(SubscriptionPriceEntityFields.GROUP, pAction.getGroup(), Operator.EQUAL));
		lCriteria.add(new CriterionDao<Object>(SubscriptionPriceEntityFields.ORDER, pAction.getSubscribers(), Operator.EQUAL));
		List<SubscriptionPrice> lEntities = dao.find(lCriteria);
		final List<SubscriptionPriceUi> lUis;
		if(CollectionUtils.isNotEmpty(lEntities)) {
			lUis = transformer.toUi(lEntities);
		} else {
			lUis = Collections.emptyList();
		}
		GetPriceResult result = new GetPriceResult();
		//Always one.
		result.setPrice(lUis.get(0));
		return result;
	}

	public Class<GetPriceAction> getActionType() {
		return GetPriceAction.class;
	}

	public void rollback(GetPriceAction pAction, GetPriceResult pResult,
			ExecutionContext pContext) throws DispatchException {
	}
	
	private void createPrice() {
		SubscriptionPrice lEntity = new SubscriptionPrice();
		lEntity.setGroup(256l);
		lEntity.setOrder(1);
		lEntity.setPrice(250.50f);
		dao.save(lEntity);
	}
}