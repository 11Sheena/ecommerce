package com.ecommerce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Product;
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{

	Optional<List<Product>> getByStatus(Boolean status);
}
