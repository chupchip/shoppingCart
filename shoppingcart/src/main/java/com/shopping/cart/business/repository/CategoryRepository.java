package com.shopping.cart.business.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import com.shopping.cart.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>, Serializable{

}
