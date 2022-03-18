package com.turkcell.rentAcar.business.requests.invoice;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	
	private long invoiceNo;

	private LocalDate createDate;
	
	private int customerId;
	
	private int rentalId;
}
