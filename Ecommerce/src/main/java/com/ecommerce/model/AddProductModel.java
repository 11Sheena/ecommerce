package com.ecommerce.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductModel {
	@NotNull(message = "Product Name cannot be null")
    @NotBlank(message = "Product Name cannot be empty")
	private String productName;
	@NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be empty")
	private String category;
	@NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be empty")
	private String description;
	@NotNull(message = "Password cannot be null")
	private Double price;
}
