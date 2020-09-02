package com.shopping.cart.business.service;

import com.shopping.cart.entity.ShoppingCart;

public interface DeliveryService {
	
	Double getDeliveryDiscounts(ShoppingCart shoppingCart);
	
}
