package com.shopping.cart.business.service;

import java.util.Map;

import com.shopping.cart.entity.Category;
import com.shopping.cart.entity.DiscountTypeEnum;
import com.shopping.cart.entity.ShoppingCart;

public interface CampaignService {
	
	
	Double getCampaignMaxRateWithItemAndCategory(Category category, Long itemQuantity);
	
	Double applyCampaignsRate(Double costAmount, Double discountRate);

	void addCampaign(Category category, Double campaignRate, Long itemQuantity, DiscountTypeEnum discountTypeEnum);
	
	Map<Category, Double> calculateCampaignDiscount(ShoppingCart shoppingCart, Map<Category, Double> categoryAndCost);
}
