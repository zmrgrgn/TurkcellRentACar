package com.turkcell.rentAcar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentAcar.entities.concretes.Invoice;

public interface InvoiceDao extends JpaRepository<Invoice, Integer>{
	Invoice getInvoiceById(int invoiceId);
	List<Invoice> getAllByCustomerId(int id);
	List<Invoice> findByCreateDateBetween(LocalDate startDate, LocalDate finishDate);
	Invoice getInvoiceByInvoiceNo(long invoiceNo);
}
