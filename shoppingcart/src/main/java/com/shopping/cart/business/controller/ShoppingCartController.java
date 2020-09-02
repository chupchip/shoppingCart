package com.shopping.cart.business.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.cart.business.service.ShoppingCartDetailService;
import com.shopping.cart.business.service.ShoppingCartService;
import com.shopping.cart.entity.ShoppingCart;
import com.shopping.cart.exceptions.TotalCostNotFoundException;
import com.shopping.cart.model.ShopingCartDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shoppingCart")
@RequiredArgsConstructor
public class ShoppingCartController {

	private final ShoppingCartService shoppingCartService;
	private final ShoppingCartDetailService shoppingCartDetailService;

	@PostMapping("/addItem")
	public void addItem(ShopingCartDTO shopingCartDTO) throws Exception {
		shoppingCartDetailService.addItem(shopingCartDTO);
	}

	@GetMapping(value = "/calculateTotalCost/{shoppingCart}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Double calculateTotalCost(@PathVariable ShoppingCart shoppingCart) {

		return shoppingCartDetailService.calculateTotalCost(shoppingCart);
	}

	@GetMapping(value = "/calculateTotalAmountAfterDiscounts/{shoppingCart}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Double calculateTotalAmountAfterDiscounts(@PathVariable ShoppingCart shoppingCart)
			throws TotalCostNotFoundException {

		if (shoppingCart.getTotalCost() == null)
			throw new TotalCostNotFoundException();

		return shoppingCartService.getTotalAmountAfterDiscounts(shoppingCart);
	}

	@GetMapping(value = "/calculateCampaignDiscounts/{shoppingCart}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Double calculateCampaignDiscounts(@PathVariable ShoppingCart shoppingCart) throws TotalCostNotFoundException {

		if (shoppingCart.getTotalCost() == null)
			throw new TotalCostNotFoundException();

		return shoppingCartService.getCampaignDiscounts(shoppingCart);
	}

	@GetMapping(value = "/calculateCouponDiscounts/{shoppingCart}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Double calculateCouponDiscounts(@PathVariable ShoppingCart shoppingCart) throws TotalCostNotFoundException {

		if (shoppingCart.getTotalCost() == null)
			throw new TotalCostNotFoundException();

		return shoppingCartService.getCouponDiscounts(shoppingCart);
	}

	@GetMapping(value = "/calculateDeliveryDiscounts/{shoppingCart}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Double calculateDeliveryDiscounts(@PathVariable ShoppingCart shoppingCart)
			throws TotalCostNotFoundException {

		if (shoppingCart.getTotalCost() == null)
			throw new TotalCostNotFoundException();

		return shoppingCartService.getDeliveryDiscounts(shoppingCart);
	}

}
