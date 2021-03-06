package com.asptttoulousenatation.core.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierStatutEnum;

public class DossierUtils {

	public static boolean isValid(DossierEntity dossier) {
		final boolean result;
		if (StringUtils.isBlank(dossier.getStatut())) {
			result = false;
		} else {
			DossierStatutEnum dossierStatut = DossierStatutEnum.valueOf(dossier.getStatut());
			result = DossierStatutEnum.INSCRIT.equals(dossierStatut)
					|| DossierStatutEnum.PAIEMENT_PARTIEL.equals(dossierStatut)
					|| DossierStatutEnum.PAIEMENT_COMPLET.equals(dossierStatut);
		}
		return result;
	}
	
	public static DateTime getDateNaissanceAsDateTime(DossierNageurEntity nageur) {
		return new DateTime(nageur.getNaissance().getTime());
	}
}
