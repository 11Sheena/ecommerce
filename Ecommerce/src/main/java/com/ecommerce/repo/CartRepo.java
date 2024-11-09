package com.ecommerce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.Customer;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer>{
	Optional<Cart> findByCustomer(Customer customer);
}
