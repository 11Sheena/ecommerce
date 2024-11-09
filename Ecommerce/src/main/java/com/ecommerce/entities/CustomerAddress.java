package com.ecommerce.entities;

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
public class CustomerAddress {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid")
    private Integer aid;

    @ManyToOne
    @JoinColumn(name = "customer", nullable = false)
    private Customer customer;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "area")
    private String area;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "type")
    private String type;

	public CustomerAddress(Customer customer, String houseNumber, String area, String city, String state, String type) {
		super();
		this.customer = customer;
		this.houseNumber = houseNumber;
		this.area = area;
		this.city = city;
		this.state = state;
		this.type = type;
	}

}
