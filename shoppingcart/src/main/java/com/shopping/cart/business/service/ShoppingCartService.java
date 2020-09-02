package com.shopping.cart.business.service;

import com.shopping.cart.entity.ShoppingCart;

public interface ShoppingCartService {

	Double getTotalAmountAfterDiscounts(ShoppingCart shoppingCart);

	Double getDeliveryDiscounts(ShoppingCart shoppingCart);

	Double getCampaignDiscounts(ShoppingCart shoppingCart);

	Double getCouponDiscounts(ShoppingCart shoppingCart);
}
