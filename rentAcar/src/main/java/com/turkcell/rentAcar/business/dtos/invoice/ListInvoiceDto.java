package com.turkcell.rentAcar.business.dtos.invoice;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListInvoiceDto {
	
	private int id;

	private long invoiceNo;

	private LocalDate createDate;

	private LocalDate rentDate;

	private LocalDate returnDate;

	private int totalDay;

	private double rentTotalPrice;

	private int customerId;

	private int rentalId;
}
