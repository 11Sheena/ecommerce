package com.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer orderItemId;
	
	@ManyToOne
	@JoinColumn
	private Orders order;
	
	@ManyToOne
	@JoinColumn
	private Product product;
	
	public OrderItems(Orders order, Product product, Integer quantity) {
		super();
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	@Column
	private Integer quantity;
}
