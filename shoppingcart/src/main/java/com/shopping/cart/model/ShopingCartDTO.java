package com.shopping.cart.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopingCartDTO {

	private Long productId;
	private Long quantity;
	private Long clientId;

}
