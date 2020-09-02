package com.shopping.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.shopping.cart.business.repository.CategoryRepository;
import com.shopping.cart.business.service.CategoryService;
import com.shopping.cart.business.service.CategoryServiceImpl;
import com.shopping.cart.entity.Category;
import com.shopping.cart.entity.Product;
import com.shopping.cart.entity.ShoppingCart;
import com.shopping.cart.entity.ShoppingCartDetail;

public class CategoryServiceTest {

	@Test
	public void shouldReturnNumberOfCategory() {
		Category foodCategory = new Category("food");
		Category furnitureCategory = new Category("furniture");

		ShoppingCartDetail shoppingCartDetailApple = new ShoppingCartDetail(UUID.randomUUID().getMostSignificantBits(), new Product(UUID.randomUUID().getMostSignificantBits(), foodCategory, "apple", 2.89),
				3L, (3 * 2.89));
		ShoppingCartDetail shoppingCartDetailAlmond = new ShoppingCartDetail(UUID.randomUUID().getMostSignificantBits(), 
				new Product(UUID.randomUUID().getMostSignificantBits(), furnitureCategory, "table", 564.50), 1L, (1 * 564.50));

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setShoppingCartDetailList(
				new ArrayList<ShoppingCartDetail>(Arrays.asList(shoppingCartDetailApple, shoppingCartDetailAlmond)));

		final CategoryRepository categoryRepositoryMock = Mockito.mock(CategoryRepository.class);
		final CategoryService categoryService = new CategoryServiceImpl(categoryRepositoryMock);

		Assert.assertEquals(Long.valueOf(2), categoryService.calculateNumberOfCategory(shoppingCart));

	}

}
