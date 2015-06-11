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
		
		
		GroupUi lUi = new GroupUi();
		lUi.setId(pEntity.getId());
		lUi.setTitle(pEntity.getTitle());
		lUi.setLicenceFfn(BooleanUtils.toBoolean(pEntity.getLicenceFfn()));
		lUi.setInscription(BooleanUtils.toBoolean(pEntity.getInscription()));
		lUi.setTarif(tarif);
		lUi.setTarif2(tarif2);
		lUi.setTarif3(tarif3);
		lUi.setTarif4(tarif4);
		lUi.setSeanceunique(BooleanUtils.toBoolean(pEntity.getSeanceunique()));
		lUi.setTarifWeight(tarifWeight);
		lUi.setNouveau(BooleanUtils.toBoolean(pEntity.getNouveau()));
		lUi.setDescription(pEntity.getDescription());
		lUi.setEnf(BooleanUtils.toBoolean(pEntity.getEnf()));
		lUi.setCompetition(BooleanUtils.toBoolean(pEntity.getCompetition()));
		return lUi;
	}

}
