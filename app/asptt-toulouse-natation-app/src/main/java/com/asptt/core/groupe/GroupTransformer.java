package com.asptt.core.groupe;

import org.apache.commons.lang3.BooleanUtils;

import com.asptt.core.lang.AbstractEntityTransformer;
import com.asptt.core.server.dao.entity.club.group.GroupEntity;

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
		
		int tarifUnique = pEntity.getTarifUnique() == null ? 0 : pEntity.getTarifUnique();
		int tarifUnique2 = pEntity.getTarifUnique2() == null ? 0 : pEntity.getTarifUnique2();
		int tarifUnique3 = pEntity.getTarifUnique3() == null ? 0 : pEntity.getTarifUnique3();
		int tarifUnique4 = pEntity.getTarifUnique4() == null ? 0 : pEntity.getTarifUnique4();
		
		
		GroupUi lUi = new GroupUi();
		lUi.setId(pEntity.getId());
		lUi.setTitle(pEntity.getTitle());
		lUi.setLicenceFfn(BooleanUtils.toBoolean(pEntity.getLicenceFfn()));
		lUi.setInscription(BooleanUtils.toBoolean(pEntity.getInscription()));
		lUi.setTarif(tarif);
		lUi.setTarif2(tarif2);
		lUi.setTarif3(tarif3);
		lUi.setTarif4(tarif4);
		lUi.setTarifUnique(tarifUnique);
		lUi.setTarifUnique2(tarifUnique2);
		lUi.setTarifUnique3(tarifUnique3);
		lUi.setTarifUnique4(tarifUnique4);
		lUi.setSeanceunique(BooleanUtils.toBoolean(pEntity.getSeanceunique()));
		lUi.setTarifWeight(tarifWeight);
		lUi.setNouveau(BooleanUtils.toBoolean(pEntity.getNouveau()));
		lUi.setDescription(pEntity.getDescription());
		lUi.setEnf(BooleanUtils.toBoolean(pEntity.getEnf()));
		lUi.setCompetition(BooleanUtils.toBoolean(pEntity.getCompetition()));
		lUi.setSecondes(BooleanUtils.toBoolean(pEntity.getSecondes()));
		return lUi;
	}

}
