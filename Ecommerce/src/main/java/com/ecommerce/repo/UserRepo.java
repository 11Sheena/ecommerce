package com.ecommerce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.User;
@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);
}
