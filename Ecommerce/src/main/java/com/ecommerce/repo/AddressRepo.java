package com.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.CustomerAddress;
@Repository
public interface AddressRepo extends JpaRepository<CustomerAddress, Integer>{

}
