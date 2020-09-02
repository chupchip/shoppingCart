package com.shopping.cart.exceptions;

public class TotalCostNotFoundException extends Exception {

	private static final long serialVersionUID = -988048544185559831L;

	public TotalCostNotFoundException() {
		super("Total cost shouldn't be empty");
	}
}
