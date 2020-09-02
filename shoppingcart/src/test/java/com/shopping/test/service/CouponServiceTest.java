package com.shopping.test.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.shopping.cart.business.repository.CouponRepository;
import com.shopping.cart.business.service.CouponService;
import com.shopping.cart.business.service.CouponServiceImpl;
import com.shopping.cart.entity.Coupon;
import com.shopping.cart.entity.DiscountTypeEnum;

public class CouponServiceTest {

	@Test
	public void shouldReturnCouponWithTotalCostAmount() {
		Double totalCostAmount = 200.0;
		Coupon coupon = new Coupon(0.20,DiscountTypeEnum.RATE,150.0);

		final CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
		final CouponService couponService  = new CouponServiceImpl(couponRepository);

		Mockito.when(couponRepository.findByMinAmountLessThanEqual(totalCostAmount))
				.thenReturn(Optional.of(coupon));

		Assert.assertEquals(Double.valueOf(0.20), couponService.getCouponWithTotalCostAmount(totalCostAmount));
	}	
}
