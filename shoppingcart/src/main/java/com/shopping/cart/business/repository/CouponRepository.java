package com.shopping.cart.business.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.shopping.cart.entity.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Long> {

	Optional<Coupon> findByMinAmountLessThanEqual(Double minAmount);

	
}
