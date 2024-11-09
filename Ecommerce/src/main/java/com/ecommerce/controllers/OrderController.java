package com.ecommerce.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entities.User;
import com.ecommerce.model.AddressModel;
import com.ecommerce.services.OrdersService;
import com.ecommerce.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrdersService orderService;
	
	@PostMapping("/placeorder")
	public ResponseEntity<ApiResponse> placeOrder(@RequestBody AddressModel model) {
		User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApiResponse response = orderService.placeOrder(user, model);
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);
	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<ApiResponse> orderByCustomerId(@PathVariable(name="customerId") Integer cId) {
		ApiResponse response = orderService.orderByCustomerId(cId);
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);
	}
	
	
	
	
}
