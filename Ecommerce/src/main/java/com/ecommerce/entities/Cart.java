package com.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")
	private Integer cartId;
	
	@ManyToOne
	@JoinColumn
	private Customer customer;
	
	@Column(name="status")
	private Boolean status;

	public Cart(Customer customer, Boolean status) {
		super();
		this.customer = customer;
		this.status = status;
	}
	
}
