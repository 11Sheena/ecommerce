package com.ecommerce.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCartModel {
	@NotNull(message = "Product ID cannot be null")
	private Integer productId;
	@NotNull(message = "Quantity cannot be null")
	@Min(value = 1, message = "The quantity must be at least 1")
	private Integer quantity;
	public AddCartModel(@NotNull(message = "Product ID cannot be null") Integer productId) {
		super();
		this.productId = productId;
	}
	
	
}
