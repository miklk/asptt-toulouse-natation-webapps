package com.asptttoulousenatation.core.server.dao.entity.club.group;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
public class CreneauHierarchyEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long parent;
	private Long child;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
	}
	public Long getChild() {
		return child;
	}
	public void setChild(Long child) {
		this.child = child;
	}
}
