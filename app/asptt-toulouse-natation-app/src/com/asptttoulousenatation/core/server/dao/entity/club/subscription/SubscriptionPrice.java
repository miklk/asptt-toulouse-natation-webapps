package com.asptttoulousenatation.core.server.dao.entity.club.subscription;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SubscriptionPrice implements Entity {

	/**	**/
	private static final long serialVersionUID = 6273596693449577130L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private Long group;
	
	@Persistent
	private float price;
	
	@Persistent
	private int order;
	
	public SubscriptionPrice() {
		
	}

	public SubscriptionPrice(Long pId, Long pGroup, float pPrice, int pOrder) {
		super();
		id = pId;
		group = pGroup;
		price = pPrice;
		order = pOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long pGroup) {
		group = pGroup;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float pPrice) {
		price = pPrice;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int pOrder) {
		order = pOrder;
	}
}