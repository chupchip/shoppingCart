package com.shopping.cart.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
	@Id
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
	private Category category;
	
	private String title;
	
	private Double price;
	
}
