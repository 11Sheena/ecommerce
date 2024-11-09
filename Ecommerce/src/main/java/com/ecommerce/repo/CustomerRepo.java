package com.ecommerce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Customer;
import com.ecommerce.entities.User;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{
	Optional<Customer> findByUser(User user);
}
