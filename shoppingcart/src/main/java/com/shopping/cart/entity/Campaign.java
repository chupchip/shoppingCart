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
public class Campaign extends Discount {
	@Id
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;

	private Long itemQuantity;

	public Campaign(Long id, Double discountRate, DiscountTypeEnum discountTypeEnum, Category category, Long itemQuantity) {
		super(discountRate, discountTypeEnum);
		this.id = id;
		this.category = category;
		this.itemQuantity = itemQuantity;
	}

}
