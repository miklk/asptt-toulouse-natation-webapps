package com.asptttoulousenatation.server.userspace.admin.entity;

import org.apache.commons.lang3.BooleanUtils;

import com.asptttoulousenatation.core.groupe.GroupUi;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;

public class GroupTransformer extends
		AbstractEntityTransformer<GroupUi, GroupEntity> {
	
	private static GroupTransformer INSTANCE;
	
	public static GroupTransformer getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new GroupTransformer();
		}
		return INSTANCE;
	}

	@Override
	public GroupUi toUi(GroupEntity pEntity) {
		int tarif = pEntity.getTarif() == null ? 0 : pEntity.getTarif();
		int tarif2 = pEntity.getTarif2() == null ? 0 : pEntity.getTarif2();
		int tarif3 = pEntity.getTarif3() == null ? 0 : pEntity.getTarif3();
		int tarif4 = pEntity.getTarif4() == null ? 0 : pEntity.getTarif4();
		int tarifWeight = pEntity.getTarifWeight() == null ? 0 : pEntity.getTarifWeight();
		GroupUi lUi = new GroupUi(pEntity.getId(), pEntity.getTitle(),
				BooleanUtils.toBoolean(pEntity.getLicenceFfn()),
				BooleanUtils.toBoolean(pEntity.getInscription()), tarif,
				tarif2, tarif3, tarif4, BooleanUtils.toBoolean(pEntity.getSeanceunique()), tarifWeight, BooleanUtils.toBoolean(pEntity.getNouveau()), pEntity.getDescription(), BooleanUtils.toBoolean(pEntity.getEnf()));
		return lUi;
	}

}
