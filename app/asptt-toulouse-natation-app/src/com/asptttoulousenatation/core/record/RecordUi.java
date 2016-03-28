package com.asptttoulousenatation.core.record;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.core.server.dao.entity.record.RecordEntity;
import com.asptttoulousenatation.core.server.dao.entity.record.RecordEpreuveEntity;

public class RecordUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RecordEpreuveEntity epreuve;
	private List<RecordEntity> records;
	
	public RecordUi() {
		records = new ArrayList<>();
	}
	
	public void addRecord(RecordEntity record) {
		records.add(record);
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