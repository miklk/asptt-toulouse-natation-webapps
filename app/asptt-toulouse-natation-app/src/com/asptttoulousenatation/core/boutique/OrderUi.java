package com.asptttoulousenatation.core.boutique;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.boutique.OrderEntity;
import com.asptttoulousenatation.core.server.dao.entity.boutique.ProductEntity;
import com.asptttoulousenatation.web.creneau.CoupleValue;

@XmlRootElement
public class OrderUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderEntity order;
	private List<CoupleValue<ProductEntity, Integer>> produits = new ArrayList<>();
	
	public void addProduit(ProductEntity produit, Integer quantite) {
		produits.add(new CoupleValue<ProductEntity, Integer>(produit, quantite));
	}
	
	public OrderEntity getOrder() {
		return order;
	}
	public void setOrder(OrderEntity order) {
		this.order = order;
	}
	public List<CoupleValue<ProductEntity, Integer>> getProduits() {
		return produits;
	}
	public void setProduits(List<CoupleValue<ProductEntity, Integer>> produits) {
		this.produits = produits;
	}
}
