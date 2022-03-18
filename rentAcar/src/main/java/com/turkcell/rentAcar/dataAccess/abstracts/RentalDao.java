package com.turkcell.rentAcar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentAcar.entities.concretes.Rental;

@Repository
public interface RentalDao extends JpaRepository<Rental, Integer>{
	Rental getRentalById(int rentalId);
	List<Rental> getAllByCarId(int id);
	Rental getByCustomer_customerId(int customerId);
}
