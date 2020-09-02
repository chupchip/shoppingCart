package com.shopping.cart.business.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.shopping.cart.entity.Campaign;
import com.shopping.cart.entity.Category;


public interface CampaignRepository extends  CrudRepository<Campaign, Long>{
	
	Optional<Campaign> findByCategoryAndItemQuantity(Category category, Long itemQuantity);

}
