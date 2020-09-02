package com.shopping.cart.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShoppingCartDetail {
	@Id
	private Long id;

	@ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
	private Product product;
	
	private Long productsQuantity;
	
	private Double productsCost;
	
	public ShoppingCartDetail(Long id, Product product, Long productsQuantity, Double productsCost) {
		this.id = id;
		this.product = product;
		this.productsQuantity = productsQuantity;
		this.productsCost = productsCost;
	}
	
}
