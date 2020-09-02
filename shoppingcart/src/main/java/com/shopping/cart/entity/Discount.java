package com.shopping.cart.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Discount {

	private Double discountRate;
	
	private DiscountTypeEnum discountTypeEnum;

	public Discount(Double discountRate, DiscountTypeEnum discountTypeEnum) {
		this.discountRate = discountRate;
		this.discountTypeEnum = discountTypeEnum;
	}
}
