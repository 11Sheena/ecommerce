package com.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.entities.Customer;
import com.ecommerce.entities.User;
import com.ecommerce.model.SignUpModel;
import com.ecommerce.repo.CustomerRepo;
import com.ecommerce.repo.UserRepo;
import com.ecommerce.utils.ApiResponse;

@Service
public class CustomerService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CustomerRepo cusRepo;
	
	public ApiResponse saveCustomer(SignUpModel model) {
		try {
			Optional<User> ob = userRepo.findByEmail(model.getEmail());
			if(ob.isPresent()) {
				return new ApiResponse(false, "Email Already Exists");
			}
			else {
				User user = new User(model.getEmail(), passwordEncoder.encode(model.getPassword()),"ROLE_CUSTOMER", true);
				user = userRepo.save(user);
				Customer customer = new Customer(model.getName(), model.getMobile(), user);
				customer = cusRepo.save(customer);	
				return new ApiResponse(true, "SignUp Successful", customer);
			}
			
		}catch (Exception e) {
			return new ApiResponse(false, "Exception ",e.getMessage());
		}
	}
	
}
