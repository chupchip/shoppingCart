package com.shopping.cart.business.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shopping.cart.business.repository.ProductRepository;
import com.shopping.cart.entity.Category;
import com.shopping.cart.entity.Product;
import com.shopping.cart.entity.ShoppingCart;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public Long calculateNumberOfProduct(ShoppingCart shoppingCart) {
		return shoppingCart.getShoppingCartDetailList().stream().count();
	}

	@Override
	public Product addProduct(Category category, Double price, String title) {
	    return productRepository.save(new Product(UUID.randomUUID().getMostSignificantBits(), category,title,price));
	}

	@Override
	public Optional<Product> findById(Long productId) {
		return productRepository.findById(productId);
	}

}
