package com.shopping.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.shopping.cart.business.repository.CampaignRepository;
import com.shopping.cart.business.service.CampaignService;
import com.shopping.cart.business.service.CampaignServiceImpl;
import com.shopping.cart.entity.Campaign;
import com.shopping.cart.entity.Category;
import com.shopping.cart.entity.DiscountTypeEnum;
import com.shopping.cart.entity.Product;
import com.shopping.cart.entity.ShoppingCart;
import com.shopping.cart.entity.ShoppingCartDetail;

public class CampaignServiceTest {

	private Long itemQuantityMaxRate;
	private Long itemQuantityCorrectValue;
	private Category category;
	private Campaign campaignMaxRate;
	private Campaign campaignCorrectValue;
	private ShoppingCartDetail shoppingCartDetailApple;
	private ShoppingCartDetail shoppingCartDetailAlmond;
	private Map<Category, Double> categoryAndCost;
	private ShoppingCart shoppingCart;

	@Before
	public void init() {
		itemQuantityMaxRate = 2L;
		itemQuantityCorrectValue = 8L;

		category = new Category("food");

		campaignMaxRate = new Campaign(UUID.randomUUID().getMostSignificantBits(), 0.2, DiscountTypeEnum.RATE, category,
				itemQuantityMaxRate);
		campaignCorrectValue = new Campaign(UUID.randomUUID().getMostSignificantBits(), 0.5, DiscountTypeEnum.RATE,
				category, itemQuantityCorrectValue);

		shoppingCartDetailApple = new ShoppingCartDetail(UUID.randomUUID().getMostSignificantBits(),
				new Product(UUID.randomUUID().getMostSignificantBits(), category, "apple", 2.89), 3L, (3 * 2.89));
		shoppingCartDetailAlmond = new ShoppingCartDetail(UUID.randomUUID().getMostSignificantBits(),
				new Product(UUID.randomUUID().getMostSignificantBits(), category, "almond", 3.50), 5L, (5 * 3.50));

		categoryAndCost = new HashMap<>();

		shoppingCart = new ShoppingCart();
	}

	@Test
	public void shouldReturnCampaignMaxRateWithItemAndCategory() {

		final CampaignRepository campaignRepository = Mockito.mock(CampaignRepository.class);

		final CampaignService campaignService = new CampaignServiceImpl(campaignRepository);

		Mockito.when(campaignRepository.findByCategoryAndItemQuantity(category, itemQuantityMaxRate))
				.thenReturn(Optional.of(campaignMaxRate));

		Double campaignMaxRateWithItemAndCategory = campaignService.getCampaignMaxRateWithItemAndCategory(category,
				itemQuantityMaxRate);

		Assert.assertNotEquals(0, campaignMaxRateWithItemAndCategory);
	}

	@Test
	public void shouldCorrectValueCalculateCampaignDiscount() {

		final CampaignRepository campaignRepository = Mockito.mock(CampaignRepository.class);
		final CampaignService campaignService = new CampaignServiceImpl(campaignRepository);

		Mockito.when(campaignRepository.findByCategoryAndItemQuantity(category, itemQuantityCorrectValue))
				.thenReturn(Optional.of(campaignCorrectValue));

		Map<Category, Double> calculateCampaignDiscount = campaignService.calculateCampaignDiscount(shoppingCart,
				categoryAndCost);

		categoryAndCost.put(category, Double.valueOf(26.17));
		shoppingCart.setShoppingCartDetailList(
				new ArrayList<ShoppingCartDetail>(Arrays.asList(shoppingCartDetailApple, shoppingCartDetailAlmond)));

		Assert.assertEquals(Double.valueOf(13.085), calculateCampaignDiscount.get(category));
	}

}
