package com.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.OrderItems;
import com.ecommerce.entities.Orders;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItems, Integer>{

	List<OrderItems> findByOrder(Orders o);

}
