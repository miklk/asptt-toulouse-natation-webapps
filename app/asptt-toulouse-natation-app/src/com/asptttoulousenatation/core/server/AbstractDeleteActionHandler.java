package com.asptttoulousenatation.core.server;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.Entity;
import com.asptttoulousenatation.core.shared.AbstractDeleteAction;
import com.asptttoulousenatation.core.shared.AbstractDeleteResult;

public abstract class AbstractDeleteActionHandler<E extends Entity, A extends AbstractDeleteAction<R>, R extends AbstractDeleteResult>
		implements ActionHandler<A, R> {

	protected DaoBase<E> dao;

	public R execute(A pAction, ExecutionContext pContext)
			throws DispatchException {
		dao = createDao();
		E lEntity = dao.get(pAction.getId());
		if (lEntity != null) {
			doBeforeDelete(lEntity, pContext);
			dao.delete(lEntity);
		}
		R lResult = getResult();
		fillResult(pAction, lResult, pContext);
		return lResult;
	}

	protected abstract void doBeforeDelete(E pEntity, ExecutionContext pContext)
			throws DispatchException;
	
	protected abstract void fillResult(A pAction, R pResult, ExecutionContext pContext) throws DispatchException;
	
	public void rollback(A pAction, R pResult, ExecutionContext pContext)
			throws DispatchException {
	}

	protected abstract DaoBase<E> createDao();

	protected abstract R getResult();
}