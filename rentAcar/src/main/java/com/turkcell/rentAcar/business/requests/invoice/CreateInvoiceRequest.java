package com.turkcell.rentAcar.business.requests.invoice;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	@NotNull
	@Positive
	private long invoiceNo;
	@NotNull
	private LocalDate createDate;
	@NotNull
	@Positive
	private int customerId;
	@NotNull
	@Positive
	private int rentalId;
}
