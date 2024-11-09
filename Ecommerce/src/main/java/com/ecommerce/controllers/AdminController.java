package com.ecommerce.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.AddProductModel;
import com.ecommerce.services.OrdersService;
import com.ecommerce.services.ProductService;
import com.ecommerce.utils.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrdersService orderService;
	
	
	@PostMapping("/addproduct")
	public ResponseEntity<ApiResponse> addProduct(@Valid @RequestBody AddProductModel model) {
		
		ApiResponse response = productService.addProduct(model);
		
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);
	}
	
	@DeleteMapping("/deleteproduct/{productId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable(name="productId") Integer productId)
	{
		ApiResponse response = productService.deleteProduct(productId);
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);
	}
	
	@PatchMapping("/updateproduct/{productId}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable(name="productId") Integer productId, @RequestBody AddProductModel model) 
	{
		ApiResponse response = productService.updateProduct(productId, model);
		return null;
	}
	

	@GetMapping("/getallorders")
	public ResponseEntity<ApiResponse> getAllOrders() {
		ApiResponse response = orderService.getAllOrders();
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);
	}
	
	
}
