package com.shopping.cart.business.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shopping.cart.business.repository.CampaignRepository;
import com.shopping.cart.entity.Campaign;
import com.shopping.cart.entity.Category;
import com.shopping.cart.entity.DiscountTypeEnum;
import com.shopping.cart.entity.ShoppingCart;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

	private final CampaignRepository campaignRepository;

	
	@Override
	public Double getCampaignMaxRateWithItemAndCategory(Category category, Long itemQuantity) {
		Optional<Campaign> campaign = campaignRepository.findByCategoryAndItemQuantity(category, itemQuantity);
		return campaign.isPresent() ? campaign.get().getDiscountRate() : Double.valueOf(0);
	}
	
	@Override
	public Double applyCampaignsRate(Double categoryCostAmount, Double discountRate) { 
		return categoryCostAmount - (categoryCostAmount * discountRate); // yenifiyat donuyor
	}

	@Override
	public void addCampaign(Category category, Double campaignRate, Long itemQuantity, DiscountTypeEnum discountTypeEnum) {
		campaignRepository.save(new Campaign(UUID.randomUUID().getMostSignificantBits(), campaignRate, discountTypeEnum, category, itemQuantity));
		
	}
	
	@Override
	public Map<Category, Double>  calculateCampaignDiscount(ShoppingCart shoppingCart, Map<Category, Double> categoryAndCost) {
		
		categoryAndCost.entrySet().stream().forEach((entry) -> {
			Long productSumForSameCategory = shoppingCart.getShoppingCartDetailList().stream()
					.filter(product -> product.getProduct().getCategory().getTitle().equals(entry.getKey().getTitle()))
					.mapToLong(p -> p.getProductsQuantity()).sum();
			 
			Double campaignMaxRate = getCampaignMaxRateWithItemAndCategory(entry.getKey(),
					productSumForSameCategory);

		 entry.setValue(applyCampaignsRate(entry.getValue(), campaignMaxRate));
		});
		
		return categoryAndCost;
	}

	

}
