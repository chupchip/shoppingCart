package com.shopping.cart.business.service;

import org.springframework.stereotype.Service;

import com.shopping.cart.entity.CostConstants;
import com.shopping.cart.entity.ShoppingCart;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
	
	private final CategoryService categoryService;
	private final ProductService productService;
	
	@Override
	public Double getDeliveryDiscounts(ShoppingCart shoppingCart) {
		return (CostConstants.COST_PER_DELIVERY * categoryService.calculateNumberOfCategory(shoppingCart))
			 + (CostConstants.COST_PER_PRODUCT * productService.calculateNumberOfProduct(shoppingCart))
			 + CostConstants.FIXED_COST;
	}

	
}
