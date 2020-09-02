package com.shopping.cart.business.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import com.shopping.cart.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long>, Serializable{

}
