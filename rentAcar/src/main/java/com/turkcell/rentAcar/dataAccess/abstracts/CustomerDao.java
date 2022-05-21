package com.turkcell.rentAcar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentAcar.entities.concretes.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer>{
	Customer getCustomerByEmail(String email);
}
