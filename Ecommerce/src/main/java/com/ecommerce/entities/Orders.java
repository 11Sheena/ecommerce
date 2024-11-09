package com.ecommerce.entities;

import java.time.LocalDate;

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
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer orderId;
	
	@ManyToOne
	@JoinColumn
	private Customer customer;
	
	@Column
	private LocalDate orderDate;
	
	@ManyToOne
	@JoinColumn
	private CustomerAddress cusAddress;
	
	@Column
	private Double totalAmount;
	
	@Column
	private String status;

	public Orders(Customer customer, LocalDate orderDate, CustomerAddress cusAddress, Double totalAmount,
			String status) {
		super();
		this.customer = customer;
		this.orderDate = orderDate;
		this.cusAddress = cusAddress;
		this.totalAmount = totalAmount;
		this.status = status;
	}
}
