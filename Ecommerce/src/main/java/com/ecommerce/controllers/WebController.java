package com.ecommerce.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.config.JwtTokenUtil;
import com.ecommerce.entities.User;
import com.ecommerce.model.LoginModel;
import com.ecommerce.model.LoginResponseModel;
import com.ecommerce.model.SignUpModel;
import com.ecommerce.services.CustomerService;
import com.ecommerce.services.ProductService;
import com.ecommerce.services.UserService;
import com.ecommerce.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/web")
public class WebController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenUtil jwtToken;
	
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> signUp(@RequestBody SignUpModel model) {
		
		ApiResponse response = customerService.saveCustomer(model);
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<ApiResponse> login(@RequestBody LoginModel model) {
		try {
			authmanager.authenticate(new UsernamePasswordAuthenticationToken(model.getEmail(), model.getPassword()));
			User user = (User) userService.loadUserByUsername(model.getEmail());
			if (user.getActiveStatus()) {
				final String token = jwtToken.generateToken(user);

				LoginResponseModel lres = new LoginResponseModel(user.getEmail(), token, user.getRole());

				ApiResponse response = new ApiResponse(true, "Login Success !", lres);

				return ResponseEntity.status(200).body(response);
			} else {
				ApiResponse response = new ApiResponse(false, "Login failed");

				return ResponseEntity.status(200).body(response);
			}
		} catch (Exception ex) {
			ApiResponse response = new ApiResponse(false, "Login failed");

			return ResponseEntity.status(400).body(response);
		}
	}
	
	@GetMapping("/products")
	public ResponseEntity<ApiResponse> getAllProducts() {
		ApiResponse response = productService.allProducts();
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);
	}
	
	
}
