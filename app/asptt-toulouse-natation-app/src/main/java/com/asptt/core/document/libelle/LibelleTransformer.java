package com.asptt.core.document.libelle;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.asptt.core.server.dao.entity.document.LibelleEntity;
import com.asptt.core.shared.document.libelle.LibelleUi;

public class LibelleTransformer {

	private static LibelleTransformer INSTANCE;
	
	private static final String HIERARCHY_SEPARATOR = "/";
	
	public static LibelleTransformer getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new LibelleTransformer();
		}
		return INSTANCE;
	}
	
	public LibelleUi get(LibelleEntity entity) {
		LibelleUi ui = new LibelleUi();
		ui.setId(entity.getId());
		ui.setIntitule(entity.getIntitule());
		ui.setWholeIntitule(entity.getIntitule());
		return ui;
	}
	
	public Map<String, LibelleUi> get(List<LibelleEntity> entities) {
		Map<String, LibelleUi> uisMap = new LinkedHashMap<>();
		for(LibelleEntity entity: entities) {
			String libelle = entity.getIntitule();
			//Determine si c'est un enfant
			if(libelle.contains(HIERARCHY_SEPARATOR)) {
				String[] hierarchies = libelle.split(HIERARCHY_SEPARATOR);
				String parent = null;
				for(String hierarchy: hierarchies) {
					if (uisMap.containsKey(hierarchy)) {
						parent = hierarchy;
					} else {
						LibelleUi ui = new LibelleUi();
						ui.setWholeIntitule(hierarchy);
						ui.setIntitule(hierarchy);
						uisMap.put(hierarchy, ui);
						if (StringUtils.isNotBlank(parent)) {
							ui.setHasAncestor(true);
							LibelleUi parentUi = uisMap.get(parent);
							ui.setWholeIntitule(parent + HIERARCHY_SEPARATOR + hierarchy);
							parentUi.setHasChild(true);
							parentUi.addChild(ui);
						}
						parent = hierarchy;
					}
				}
			} else {
				if(!uisMap.containsKey(libelle)) {
					uisMap.put(libelle, get(entity));
				}
			}
		}
		return uisMap;
	}
}
