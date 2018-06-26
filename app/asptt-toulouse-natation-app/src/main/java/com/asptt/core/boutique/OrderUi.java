package com.asptt.core.boutique;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptt.core.lang.CoupleValue;
import com.asptt.core.server.dao.entity.boutique.OrderEntity;
import com.asptt.core.server.dao.entity.boutique.ProductEntity;

@XmlRootElement
public class OrderUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderEntity order;
	private List<CoupleValue<ProductEntity, Integer>> produits = new ArrayList<>();
	private List<ProductEntity> selectedProduct = new ArrayList<>();
	
	public void addProduit(ProductEntity produit, Integer quantite) {
		selectedProduct.add(produit);
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

	public List<ProductEntity> getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(List<ProductEntity> selectedProduct) {
		this.selectedProduct = selectedProduct;
	}
	
}
