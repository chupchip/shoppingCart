package com.shopping.cart.business.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.shopping.cart.business.repository.CategoryRepository;
import com.shopping.cart.entity.Category;
import com.shopping.cart.entity.ShoppingCart;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;

	@Override
	public Long calculateNumberOfCategory(ShoppingCart shoppingCart) {
		
		Set<Category> categorySet = new HashSet<>(); 
		shoppingCart.getShoppingCartDetailList()
					.stream()
					.map(shoppingCartDetail -> shoppingCartDetail.getProduct().getCategory())
					.forEach(category -> categorySet.add(category));
		
		return categorySet.stream().count();
	}

	@Override
	public Category addCategory(String title) {
		return categoryRepository.save(new Category(title));
	}

}
