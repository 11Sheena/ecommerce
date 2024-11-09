package com.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="productId")
	private Integer productId;
	
	@Column(nullable=false)
	private String productName;
	
	@Column
	private String category;
	
	@Column
	private String description;
	
	@Column(nullable=false)
	private Double price;

    @Column(name = "status", nullable = false)
    private Boolean status;

	public Product(String productName, String category, String description, Double price,
			Boolean status) {
		super();
		this.productName = productName;
		this.category = category;
		this.description = description;
		this.price = price;
		this.status = status;
	}

}
