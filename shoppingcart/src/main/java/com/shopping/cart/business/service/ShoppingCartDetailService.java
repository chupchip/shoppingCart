package com.shopping.cart.business.service;

import com.shopping.cart.entity.ShoppingCart;
import com.shopping.cart.model.ShopingCartDTO;

public interface ShoppingCartDetailService {

	Double calculateTotalCost(ShoppingCart shoppingCart);

	ShoppingCart addItem(ShopingCartDTO shopingCartDTO) throws Exception;

}
