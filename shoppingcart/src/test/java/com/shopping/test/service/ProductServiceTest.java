package com.shopping.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.shopping.cart.business.repository.ProductRepository;
import com.shopping.cart.business.service.ProductService;
import com.shopping.cart.business.service.ProductServiceImpl;
import com.shopping.cart.entity.Category;
import com.shopping.cart.entity.Product;
import com.shopping.cart.entity.ShoppingCart;
import com.shopping.cart.entity.ShoppingCartDetail;


public class ProductServiceTest {

	@Test
	public void shouldReturnNumberOfProduct(){
		Category foodCategory = new Category("food");
		Category furnitureCategory = new Category("furniture");

		ShoppingCartDetail shoppingCartDetailApple = new ShoppingCartDetail(UUID.randomUUID().getMostSignificantBits(), new Product(UUID.randomUUID().getMostSignificantBits(), foodCategory, "apple", 2.89),
				3L, (3 * 2.89));
		ShoppingCartDetail shoppingCartDetailAlmond = new ShoppingCartDetail(UUID.randomUUID().getMostSignificantBits(), 
				new Product(UUID.randomUUID().getMostSignificantBits(), furnitureCategory, "table", 564.50), 1L, (1 * 564.50));

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setShoppingCartDetailList(
				new ArrayList<ShoppingCartDetail>(Arrays.asList(shoppingCartDetailApple, shoppingCartDetailAlmond)));

		final ProductRepository productRepositoryMock = Mockito.mock(ProductRepository.class);
		
		final ProductService productService = new ProductServiceImpl(productRepositoryMock);
		
		Assert.assertEquals(Long.valueOf(2),productService.calculateNumberOfProduct(shoppingCart));

	}
	
}
