package com.ecommerce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.CartItem;
import com.ecommerce.entities.Product;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Integer>{
	Optional<CartItem> findByProduct(Product product);
	Optional<List<CartItem>> findByCart(Cart cart);
	@Query("select sp from CartItem sp where sp.cart=?1 and sp.product=?2")
	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
