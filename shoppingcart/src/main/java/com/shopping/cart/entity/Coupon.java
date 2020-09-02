package com.shopping.cart.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Coupon extends Discount {

	@Id
	private Long id;
	
	private Double minAmount;

	public Coupon(Double discountRate, DiscountTypeEnum discountTypeEnum, Double minAmount) {
		super(discountRate, discountTypeEnum);
		this.minAmount = minAmount;
	}

}
