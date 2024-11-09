package com.ecommerce.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartModel {
	
	@Min(value = 0, message = "The quantity cannot be a negative value")
	private Integer quantity;
	
	@NotNull(message = "Product ID cannot be null")
	private Integer productId;
}
