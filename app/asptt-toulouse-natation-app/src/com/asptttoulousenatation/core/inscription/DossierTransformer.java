package com.asptttoulousenatation.core.inscription;

import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.server.userspace.admin.entity.AbstractEntityTransformer;

public class DossierTransformer extends AbstractEntityTransformer<DossierUi, DossierEntity> {

	private static DossierTransformer INSTANCE;
	
	public static DossierTransformer getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new DossierTransformer();
		}
		return INSTANCE;
	}
	
	@Override
	public DossierUi toUi(DossierEntity pEntity) {
		DossierUi dossierUi = new DossierUi();
		dossierUi.setPrincipal(pEntity);
		return dossierUi;
	}

}