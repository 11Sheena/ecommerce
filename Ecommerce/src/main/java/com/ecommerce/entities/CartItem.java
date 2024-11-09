package com.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_item_id")
	private Integer cartItemId;
	
	@ManyToOne
	@JoinColumn(name="cart")
	private Cart cart;
	
	@ManyToOne
	@JoinColumn
	private Product product;
	
	@Column
	private Integer quantity;

	public CartItem(Cart cart, Product product, Integer quantity) {
		super();
		this.cart = cart;
		this.product = product;
		this.quantity = quantity;
	}
}
