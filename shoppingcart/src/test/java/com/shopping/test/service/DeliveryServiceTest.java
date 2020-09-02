package com.shopping.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.shopping.cart.business.service.CategoryService;
import com.shopping.cart.business.service.DeliveryService;
import com.shopping.cart.business.service.DeliveryServiceImpl;
import com.shopping.cart.business.service.ProductService;
import com.shopping.cart.entity.ShoppingCart;

public class DeliveryServiceTest {

	@Test
	public void shouldReturnDeliveryDiscount(){
	
		final ShoppingCart shoppingCartMock = Mockito.mock(ShoppingCart.class);

		final ProductService productServiceMock = Mockito.mock(ProductService.class);
		final CategoryService categoryServiceMock = Mockito.mock(CategoryService.class);		
		
		final DeliveryService deliveryService = new DeliveryServiceImpl(categoryServiceMock, productServiceMock); 

		Mockito.when(productServiceMock.calculateNumberOfProduct(shoppingCartMock))
				.thenReturn(Long.valueOf(6));
		Mockito.when(categoryServiceMock.calculateNumberOfCategory(shoppingCartMock))
		.thenReturn(Long.valueOf(2));
		
		Assert.assertNotEquals(Double.valueOf(0), deliveryService.getDeliveryDiscounts(shoppingCartMock));
		
	}
	
}
