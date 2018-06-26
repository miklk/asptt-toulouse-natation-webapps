package com.asptt.core.inscription;

import com.asptt.core.lang.AbstractEntityTransformer;
import com.asptt.core.server.dao.entity.inscription.DossierNageurEntity;

public class DossierNageurTransformer extends AbstractEntityTransformer<DossierUi, DossierNageurEntity> {

	private static DossierNageurTransformer INSTANCE;
	
	public static DossierNageurTransformer getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new DossierNageurTransformer();
		}
		return INSTANCE;
	}
	
	@Override
	public DossierUi toUi(DossierNageurEntity pEntity) {
		DossierUi dossierUi = new DossierUi();
		return dossierUi;
	}

}