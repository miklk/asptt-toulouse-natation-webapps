package com.asptttoulousenatation.core.server.stat;

import java.util.List;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.commons.collections.CollectionUtils;

import com.asptttoulousenatation.core.server.dao.entity.stat.VisitorCounter;
import com.asptttoulousenatation.core.server.dao.stat.VisitorCounterDao;
import com.asptttoulousenatation.core.shared.stat.IncrementVisitorCounterAction;
import com.asptttoulousenatation.core.shared.stat.IncrementVisitorCounterResult;

public class IncrementVisitorCounterActionHandler
		implements
		ActionHandler<IncrementVisitorCounterAction, IncrementVisitorCounterResult> {
	
	private static final Logger LOG = Logger.getLogger(IncrementVisitorCounterActionHandler.class
			.getName());

	private VisitorCounterDao dao = new VisitorCounterDao();
	
	@Override
	public IncrementVisitorCounterResult execute(
			IncrementVisitorCounterAction visitorCounterAction, ExecutionContext executionContext)
			throws DispatchException {
		List<VisitorCounter> counters = dao.getAll();
		if(CollectionUtils.isNotEmpty(counters)) {
			VisitorCounter counter = counters.get(0);
			counter.setCounter(counter.getCounter() + 1);
			dao.save(counter);
		} else {
			LOG.warning("No counter");
		}
		return new IncrementVisitorCounterResult();
	}

	@Override
	public Class<IncrementVisitorCounterAction> getActionType() {
		return IncrementVisitorCounterAction.class;
	}

	@Override
	public void rollback(IncrementVisitorCounterAction arg0,
			IncrementVisitorCounterResult arg1, ExecutionContext arg2)
			throws DispatchException {
	}
}