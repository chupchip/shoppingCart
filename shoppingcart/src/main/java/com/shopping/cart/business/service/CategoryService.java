package com.shopping.cart.business.service;

import com.shopping.cart.entity.Category;
import com.shopping.cart.entity.ShoppingCart;

public interface CategoryService {

	Long calculateNumberOfCategory(ShoppingCart shoppingCart);
	
	Category addCategory(String title);
}
