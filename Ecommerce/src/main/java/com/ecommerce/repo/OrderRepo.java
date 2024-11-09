package com.ecommerce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Customer;
import com.ecommerce.entities.Orders;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer>{

	Optional<List<Orders>> findByCustomer(Customer customer);

}
