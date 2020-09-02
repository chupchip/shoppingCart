package com.shopping.cart.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category {
	@Id
	private Long id;
	
	private String title;	
	
	@OneToMany(mappedBy = "category")
	private List<Product> productList;

	public Category(String title) {
		this.title = title;
	}	

}
