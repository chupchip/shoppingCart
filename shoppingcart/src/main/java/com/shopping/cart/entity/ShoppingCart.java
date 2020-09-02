package com.shopping.cart.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShoppingCart {

	@Id
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<ShoppingCartDetail> shoppingCartDetailList ;
	
	private Double totalCost;
	
	
}
