package com.shopping.cart.business.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shopping.cart.business.repository.ShoppingCartRepository;
import com.shopping.cart.entity.Product;
import com.shopping.cart.entity.ShoppingCart;
import com.shopping.cart.entity.ShoppingCartDetail;
import com.shopping.cart.model.ShopingCartDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingCartDetailServiceImpl implements ShoppingCartDetailService {

	private final ShoppingCartRepository shoppingCartRepository;
	private final ProductService productService;

	@Override
	public ShoppingCart addItem(ShopingCartDTO shopingCartDTO) throws Exception {

		Optional<Product> product = findProductOrElseThrowException(shopingCartDTO);
		Optional<ShoppingCart> customerShoppingCart = findShoppingCartOrElseThrowException(shopingCartDTO);

		addToShoppingCartDetailList(customerShoppingCart.get().getShoppingCartDetailList(),
				createShoppingCartDetail(product.get(), shopingCartDTO.getQuantity()));

		ShoppingCartDetail shoppingCartDetail = new ShoppingCartDetail(UUID.randomUUID().getMostSignificantBits(),
				product.get(), shopingCartDTO.getQuantity(),
				calculateProductsAmount(product.get().getPrice(), shopingCartDTO.getQuantity()));
		
		customerShoppingCart.get().getShoppingCartDetailList().add(shoppingCartDetail);

		return customerShoppingCart.get();

	}

	@Override
	public Double calculateTotalCost(ShoppingCart shoppingCart) {
		return shoppingCart.getShoppingCartDetailList().stream().mapToDouble(product -> product.getProductsCost())
				.sum();
	}

	private void addToShoppingCartDetailList(List<ShoppingCartDetail> shoppingCartDetailList,
			
			ShoppingCartDetail shoppingCartDetail) {
		shoppingCartDetailList.stream().forEach(product -> {
			if (product.getProduct().equals(shoppingCartDetail.getProduct())) {
				product.setProductsQuantity(product.getProductsQuantity() + shoppingCartDetail.getProductsQuantity());
				product.setProductsCost(
						calculateProductsAmount(product.getProduct().getPrice(), product.getProductsQuantity()));
			}
		});

	}

	private Optional<ShoppingCart> findShoppingCartOrElseThrowException(ShopingCartDTO shopingCartDTO) throws Exception {
		
		Optional<ShoppingCart> customerShoppingCart = shoppingCartRepository.findById(shopingCartDTO.getClientId());

		if (!customerShoppingCart.isPresent())
			throw new Exception(shopingCartDTO.getClientId() + " does not exist!");
	
		return customerShoppingCart;
	}

	private Optional<Product> findProductOrElseThrowException(ShopingCartDTO shopingCartDTO) throws Exception {
		
		Optional<Product> product = productService.findById(shopingCartDTO.getProductId());

		if (!product.isPresent())
			throw new Exception(shopingCartDTO.getProductId() + " does not exist!");
		
		return product;
	}

	private ShoppingCartDetail createShoppingCartDetail(Product product, Long quantity) {
		return new ShoppingCartDetail(UUID.randomUUID().getMostSignificantBits(), product, quantity,
				calculateProductsAmount(product.getPrice(), quantity));

	}

	private Double calculateProductsAmount(Double price, Long quantity) {
		return price * quantity;
	}

}
