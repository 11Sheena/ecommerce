package com.ecommerce.model;

import java.util.List;

import com.ecommerce.entities.Customer;
import com.ecommerce.entities.OrderItems;
import com.ecommerce.entities.Orders;

import jakarta.persistence.criteria.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AllOrderResponse {

	private Customer customer;
	private Orders orders;
	private List<OrderItems> itemList;
}
