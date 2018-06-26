package com.asptt.core.lang;

import java.util.ArrayList;
import java.util.List;

import com.asptt.core.server.dao.entity.IEntity;

public abstract class AbstractEntityTransformer<U extends Object, E extends IEntity> {
	
	public abstract U toUi(E pEntity);
	
	public List<U> toUi(List<E> pEntities) {
		List<U> lResult = new ArrayList<U>(pEntities.size());
		for(E lEntity: pEntities) {
			lResult.add(toUi(lEntity));
		}
		return lResult;
	}
}