package com.ecommerce.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entities.User;
import com.ecommerce.model.AddCartModel;
import com.ecommerce.model.UpdateCartModel;
import com.ecommerce.services.CartService;
import com.ecommerce.utils.ApiResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;



@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToCart(@RequestBody List<AddCartModel> model ) {
		
		User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApiResponse response = cartService.addToCart(user,model);
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse> deletefromCart(@RequestBody AddCartModel model)
	{
		User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApiResponse response = cartService.deletefromCart(user,model.getProductId());
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response); 
	}
	
	@GetMapping("")
	public ResponseEntity<ApiResponse> getCart() {
		User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ApiResponse response = cartService.getCart(user);
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response); 
	}
	
	@PatchMapping("/update")
	public ResponseEntity<ApiResponse> updateCart(@RequestBody UpdateCartModel model) {
		
		User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ApiResponse response = cartService.updateCart(user,model);
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);
	}
	
	
}
