package com.asptttoulousenatation.core.inscription;

import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.server.userspace.admin.entity.AbstractEntityTransformer;

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