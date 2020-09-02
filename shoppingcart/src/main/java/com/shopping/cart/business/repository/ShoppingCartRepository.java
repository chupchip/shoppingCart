package com.shopping.cart.business.repository;

import org.springframework.data.repository.CrudRepository;

import com.shopping.cart.entity.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long>{

}
