package com.turkcell.rentAcar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentAcar.entities.concretes.Payment;
@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {
	Payment getPaymentById(int id);
}
