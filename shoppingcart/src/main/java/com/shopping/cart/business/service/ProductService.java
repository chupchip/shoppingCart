package com.shopping.cart.business.service;

import java.util.Optional;

import com.shopping.cart.entity.Category;
import com.shopping.cart.entity.Product;
import com.shopping.cart.entity.ShoppingCart;

public interface ProductService {

	Long calculateNumberOfProduct(ShoppingCart shoppingCart);
	
	Product addProduct(Category category, Double price, String title);

	Optional<Product> findById(Long productId);
}
