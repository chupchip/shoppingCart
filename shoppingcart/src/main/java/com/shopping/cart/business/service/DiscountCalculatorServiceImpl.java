package com.shopping.cart.business.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shopping.cart.entity.Category;
import com.shopping.cart.entity.ShoppingCart;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscountCalculatorServiceImpl implements DiscountCalculatorService {

	private final CampaignService campaignService;
	private final CouponService couponService;

	@Override
	public ShoppingCart applyCampaign(ShoppingCart shoppingCart) {
		Map<Category, Double> categoryAndCost = getCategoryAndCost(shoppingCart);
		campaignService.calculateCampaignDiscount(shoppingCart, categoryAndCost);
		shoppingCart.setTotalCost(categoryAndCost.values().stream().reduce(0.0, Double::sum));
		
		return shoppingCart;
	}

	@Override
	public ShoppingCart applyCoupon(ShoppingCart shoppingCart) {
		
		Double couponRate = couponService.getCouponWithTotalCostAmount(shoppingCart.getTotalCost());
		shoppingCart.setTotalCost(couponService.applyCouponRate(shoppingCart.getTotalCost(), couponRate));
	
		return shoppingCart;
	}

	@Override
	public Map<Category, Double> getCategoryAndCost(ShoppingCart shoppingCart) {
		Map<Category, Double> categoryCostMap = new HashMap<>();
		shoppingCart.getShoppingCartDetailList().stream().forEach(shoppingCartDetail -> {
			Category category = shoppingCartDetail.getProduct().getCategory();
			if (categoryCostMap.containsKey(category))
				categoryCostMap.put(category, categoryCostMap.get(category) + shoppingCartDetail.getProductsCost());
			else
				categoryCostMap.put(category, shoppingCartDetail.getProductsCost());
		});
		return categoryCostMap;
	}

	@Override
	public Double calculateCampaignDiscount(ShoppingCart shoppingCart) {
		Double totalSumWithoutCampaign = shoppingCart.getShoppingCartDetailList()
													 .stream()
													 .map(shoppingCartDetail -> shoppingCartDetail.getProductsCost())
													 .collect(Collectors.summingDouble(Double::doubleValue));

		return totalSumWithoutCampaign - applyCampaign(shoppingCart).getTotalCost();
	}

	@Override
	public Double calculateCouponDiscount(ShoppingCart shoppingCart) {
		Double totalSumWithoutCoupon = shoppingCart.getShoppingCartDetailList()
													 .stream()
													 .map(shoppingCartDetail -> shoppingCartDetail.getProductsCost())
													 .collect(Collectors.summingDouble(Double::doubleValue));
											
		applyCampaign(shoppingCart).getTotalCost();
		
		return totalSumWithoutCoupon - applyCoupon(shoppingCart).getTotalCost();
	}
}
