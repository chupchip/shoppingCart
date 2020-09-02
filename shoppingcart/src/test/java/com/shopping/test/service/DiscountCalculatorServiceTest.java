package com.shopping.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.shopping.cart.business.repository.CampaignRepository;
import com.shopping.cart.business.repository.CouponRepository;
import com.shopping.cart.business.service.CampaignService;
import com.shopping.cart.business.service.CampaignServiceImpl;
import com.shopping.cart.business.service.CouponService;
import com.shopping.cart.business.service.CouponServiceImpl;
import com.shopping.cart.business.service.DiscountCalculatorService;
import com.shopping.cart.business.service.DiscountCalculatorServiceImpl;
import com.shopping.cart.entity.Campaign;
import com.shopping.cart.entity.Category;
import com.shopping.cart.entity.Coupon;
import com.shopping.cart.entity.DiscountTypeEnum;
import com.shopping.cart.entity.Product;
import com.shopping.cart.entity.ShoppingCart;
import com.shopping.cart.entity.ShoppingCartDetail;

public class DiscountCalculatorServiceTest {

	private Long foodCategoryQuantity;
	private Long furnitureCategoryQuantity;
	private Category foodCategory;
	private Category furnitureCategory;
	private Coupon coupon;
	private Campaign foodCategoryCampaign;
	private Campaign furnitureCategoryCampaign;
	private ShoppingCartDetail shoppingCartDetailApple;
	private ShoppingCartDetail shoppingCartDetailAlmond;
	private ShoppingCartDetail shoppingCartDetailFurniture;
	private ShoppingCart shoppingCart;

	@Before
	public void init() {
		foodCategoryQuantity = 8L;
		furnitureCategoryQuantity = 1L;

		foodCategory = new Category("food");
		furnitureCategory = new Category("furniture");

		coupon = new Coupon(0.2, DiscountTypeEnum.RATE, 15.0);

		foodCategoryCampaign = new Campaign(UUID.randomUUID().getMostSignificantBits(), 0.5, DiscountTypeEnum.RATE,
				foodCategory, foodCategoryQuantity);
		furnitureCategoryCampaign = new Campaign(UUID.randomUUID().getMostSignificantBits(), 0.0, DiscountTypeEnum.RATE,
				foodCategory, furnitureCategoryQuantity);

		shoppingCartDetailApple = new ShoppingCartDetail(UUID.randomUUID().getMostSignificantBits(),
				new Product(UUID.randomUUID().getMostSignificantBits(), foodCategory, "apple", 2.89), 3L, (3 * 2.89)); // 8,67
		shoppingCartDetailAlmond = new ShoppingCartDetail(UUID.randomUUID().getMostSignificantBits(),
				new Product(UUID.randomUUID().getMostSignificantBits(), foodCategory, "almond", 5.0), 5L, (3 * 5.0)); // 15,0
		shoppingCartDetailFurniture = new ShoppingCartDetail(UUID.randomUUID().getMostSignificantBits(),
				new Product(UUID.randomUUID().getMostSignificantBits(), furnitureCategory, "table", 564.50), 1L,
				(1 * 564.50));

		shoppingCart = new ShoppingCart();
		shoppingCart.setShoppingCartDetailList(new ArrayList<ShoppingCartDetail>(
				Arrays.asList(shoppingCartDetailApple, shoppingCartDetailAlmond, shoppingCartDetailFurniture)));

	}

	@Test
	public void shouldReturnCategoryAndCost() {

		final CampaignService campaignServiceMock = Mockito.mock(CampaignService.class);
		final CouponService couponServiceMock = Mockito.mock(CouponService.class);

		final DiscountCalculatorService discountCalculatorService = new DiscountCalculatorServiceImpl(
				campaignServiceMock, couponServiceMock);

		Map<Category, Double> categoryAndCostMap = discountCalculatorService.getCategoryAndCost(shoppingCart);

		Assert.assertEquals(Double.valueOf((3 * 2.89) + (3 * 5.0)), categoryAndCostMap.get(foodCategory));
		Assert.assertEquals(Double.valueOf((1 * 564.50)), categoryAndCostMap.get(furnitureCategory));

	}

	@Test
	public void shouldApplyCampaign() {

		final CampaignRepository campaignRepository = Mockito.mock(CampaignRepository.class);
		final CouponService couponServiceMock = Mockito.mock(CouponService.class);

		final CampaignService campaignService = new CampaignServiceImpl(campaignRepository);
		final DiscountCalculatorService discountCalculatorService = new DiscountCalculatorServiceImpl(campaignService,
				couponServiceMock);

		Mockito.when(campaignRepository.findByCategoryAndItemQuantity(foodCategory, foodCategoryQuantity))
				.thenReturn(Optional.of(foodCategoryCampaign));
		Mockito.when(campaignRepository.findByCategoryAndItemQuantity(furnitureCategory, furnitureCategoryQuantity))
				.thenReturn(Optional.of(furnitureCategoryCampaign));

		Assert.assertEquals(Double.valueOf(576.34), Double.valueOf(
				Math.round(discountCalculatorService.applyCampaign(shoppingCart).getTotalCost() * 100.0) / 100.0));
	}

	@Test
	public void shouldApplyCoupon() {

		final CampaignService campaignService = Mockito.mock(CampaignService.class);
		final CouponRepository couponRepository = Mockito.mock(CouponRepository.class);

		final CouponService couponService = new CouponServiceImpl(couponRepository);
		final DiscountCalculatorService discountCalculatorService = new DiscountCalculatorServiceImpl(campaignService,
				couponService);

		shoppingCart.setTotalCost(Double.valueOf(15.0));

		Mockito.when(couponRepository.findByMinAmountLessThanEqual(15.0)).thenReturn(Optional.of(coupon));

		Assert.assertEquals(Double.valueOf(12.0), discountCalculatorService.applyCoupon(shoppingCart).getTotalCost());

	}

	@Test 
	public void shouldCalculateCouponDiscount() {

		final CampaignRepository campaignRepository = Mockito.mock(CampaignRepository.class);
		final CouponRepository couponRepository = Mockito.mock(CouponRepository.class);

		final CampaignService campaignService = new CampaignServiceImpl(campaignRepository);
		final CouponService couponService = new CouponServiceImpl(couponRepository);
		final DiscountCalculatorService discountCalculatorService = new DiscountCalculatorServiceImpl(campaignService,
				couponService);

		Mockito.when(campaignRepository.findByCategoryAndItemQuantity(foodCategory, foodCategoryQuantity))
				.thenReturn(Optional.of(foodCategoryCampaign));
		Mockito.when(campaignRepository.findByCategoryAndItemQuantity(furnitureCategory, furnitureCategoryQuantity))
				.thenReturn(Optional.of(furnitureCategoryCampaign));
		Mockito.when(couponRepository.findByMinAmountLessThanEqual(576.335)).thenReturn(Optional.of(coupon));

		Assert.assertNotEquals(Double.valueOf(576.335), Double
				.valueOf(Math.round(discountCalculatorService.calculateCouponDiscount(shoppingCart) * 100.0) / 100.0));

	}

	@Test
	public void shouldCalculateCampaignDiscount() {

		final CampaignRepository campaignRepository = Mockito.mock(CampaignRepository.class);
		final CouponService couponServiceMock = Mockito.mock(CouponService.class);

		final CampaignService campaignService = new CampaignServiceImpl(campaignRepository);
		final DiscountCalculatorService discountCalculatorService = new DiscountCalculatorServiceImpl(campaignService,
				couponServiceMock);

		shoppingCart.setTotalCost(Double.valueOf((3 * 2.89) + (3 * 5.0) + (1 * 564.50)));

		Mockito.when(campaignRepository.findByCategoryAndItemQuantity(foodCategory, foodCategoryQuantity))
				.thenReturn(Optional.of(foodCategoryCampaign));
		Mockito.when(campaignRepository.findByCategoryAndItemQuantity(furnitureCategory, furnitureCategoryQuantity))
				.thenReturn(Optional.of(furnitureCategoryCampaign));

		Assert.assertEquals(Double.valueOf(11.83), Double.valueOf(
				Math.round(discountCalculatorService.calculateCampaignDiscount(shoppingCart) * 100.0) / 100.0));

	}

}
