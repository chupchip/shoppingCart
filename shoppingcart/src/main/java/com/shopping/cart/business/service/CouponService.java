package com.shopping.cart.business.service;

import com.shopping.cart.entity.DiscountTypeEnum;

public interface CouponService {
	
	Double applyCouponRate(Double totalCostAmount, Double discountRate);
	
	Double getCouponWithTotalCostAmount(Double totalCostAmount);
	
	void addCoupon(Double discountRate, DiscountTypeEnum discountTypeEnum, Double minAmount);
}
