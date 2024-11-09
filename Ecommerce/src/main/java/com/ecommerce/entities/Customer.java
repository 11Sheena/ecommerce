package com.ecommerce.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Customer{
	 	@Id
	    @Column(name = "cid")
	 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer cid;

	    @Column(name = "name", nullable = false)
	    private String name;

	    @Column(name = "phone", nullable = false, unique = true)
	    private String phone;
	    
	    @JsonIgnore
	    @ManyToOne
		@JoinColumn(name = "user", nullable = false)
		private User user;
		public Customer(String name, String phone, User user) {
			super();
			this.name = name;
			this.phone = phone;
			this.user = user;
		}
	    
	    
}
