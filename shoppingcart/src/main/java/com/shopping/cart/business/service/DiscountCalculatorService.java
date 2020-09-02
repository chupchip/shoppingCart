package com.shopping.cart.business.service;

import java.util.Map;

import com.shopping.cart.entity.Category;
import com.shopping.cart.entity.ShoppingCart;

public interface DiscountCalculatorService {

	ShoppingCart applyCampaign(ShoppingCart shoppingCart);

	ShoppingCart applyCoupon(ShoppingCart shoppingCart);

	Map<Category, Double> getCategoryAndCost(ShoppingCart shoppingCart);

	Double calculateCouponDiscount(ShoppingCart shoppingCart);

	Double calculateCampaignDiscount(ShoppingCart shoppingCart);
}
