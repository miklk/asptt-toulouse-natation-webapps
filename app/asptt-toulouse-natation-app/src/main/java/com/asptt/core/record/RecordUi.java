package com.asptt.core.record;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asptt.core.server.dao.entity.record.RecordEntity;
import com.asptt.core.server.dao.entity.record.RecordEpreuveEntity;

public class RecordUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Map<String, Integer> AGE_INDEX;
	private RecordEpreuveEntity epreuve;
	private List<RecordEntity> records;

	static {
		AGE_INDEX = new HashMap<>();
		AGE_INDEX.put("Toutes Catégories", 0);
		AGE_INDEX.put("17 ans (Cadet)", 1);
		AGE_INDEX.put("16 ans (Cadet)", 2);
		AGE_INDEX.put("15 ans (Minime)", 3);
		AGE_INDEX.put("14 ans (Minime)", 4);
		AGE_INDEX.put("13 ans (Benjamin)", 5);
		AGE_INDEX.put("12 ans (Benjamin)", 6);
	}

	public RecordUi() {
		records = new ArrayList<>(7);
		records.add(null);
		records.add(null);
		records.add(null);
		records.add(null);
		records.add(null);
		records.add(null);
		records.add(null);
	}

	public void addRecord(RecordEntity record) {
		int index = AGE_INDEX.get(record.getAge());
		records.set(index,  record);
	}

	public RecordEpreuveEntity getEpreuve() {
		return epreuve;
	}

	public void setEpreuve(RecordEpreuveEntity epreuve) {
		this.epreuve = epreuve;
	}

	public List<RecordEntity> getRecords() {
		return records;
	}

	public void setRecords(List<RecordEntity> records) {
		this.records = records;
	}

}