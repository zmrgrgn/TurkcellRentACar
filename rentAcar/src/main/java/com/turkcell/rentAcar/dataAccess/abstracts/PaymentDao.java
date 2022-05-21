package com.turkcell.rentAcar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentAcar.entities.concretes.Payment;
@Transactional
@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {
	Payment getPaymentById(int id);
	Payment getPaymentByOrderedAdditionalServiceId(int orderedAdditionalServiceId);
	Payment getPaymentByInvoiceId(int invoiceId);
}
