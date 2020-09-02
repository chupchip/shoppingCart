package com.shopping.cart.business.service;

import org.springframework.stereotype.Service;

import com.shopping.cart.entity.ShoppingCart;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private final DeliveryService deliveryService;
	private final DiscountCalculatorService discountCalculatorService;

	@Override
	public Double getTotalAmountAfterDiscounts(ShoppingCart shoppingCart) {
		discountCalculatorService.applyCampaign(shoppingCart);
		discountCalculatorService.applyCoupon(shoppingCart);
		return shoppingCart.getTotalCost();
	}

	@Override
	public Double getCampaignDiscounts(ShoppingCart shoppingCart) {
		return discountCalculatorService.calculateCampaignDiscount(shoppingCart);
	}

	@Override
	public Double getCouponDiscounts(ShoppingCart shoppingCart) {
		return discountCalculatorService.calculateCouponDiscount(shoppingCart);
    }

	@Override
	public Double getDeliveryDiscounts(ShoppingCart shoppingCart) {
		return deliveryService.getDeliveryDiscounts(shoppingCart);
	}

}
