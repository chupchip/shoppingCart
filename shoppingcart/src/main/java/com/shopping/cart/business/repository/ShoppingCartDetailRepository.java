package com.shopping.cart.business.repository;

import org.springframework.data.repository.CrudRepository;

import com.shopping.cart.entity.ShoppingCartDetail;

public interface ShoppingCartDetailRepository extends CrudRepository<ShoppingCartDetail, Long> {

}
