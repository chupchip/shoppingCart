package com.shopping.cart.business.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shopping.cart.business.repository.CouponRepository;
import com.shopping.cart.entity.Coupon;
import com.shopping.cart.entity.DiscountTypeEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

	private final CouponRepository couponRepository;

	@Override
	public void addCoupon(Double discountRate, DiscountTypeEnum discountTypeEnum, Double minAmount) {
		couponRepository.save(new Coupon(discountRate, discountTypeEnum, minAmount));
	}
	
	@Override
	public Double getCouponWithTotalCostAmount(Double totalCostAmount) {
		Optional<Coupon> coupon = couponRepository.findByMinAmountLessThanEqual(totalCostAmount);
		return coupon.isPresent() ? coupon.get().getDiscountRate() : Double.valueOf(0);
	}
	
	@Override
	public Double applyCouponRate(Double totalCostAmount, Double discountRate) { 
		return totalCostAmount - (totalCostAmount * discountRate); // yenifiyat donuyor
	}

}
