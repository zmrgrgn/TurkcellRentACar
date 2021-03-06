package com.turkcell.rentAcar.business.requests.payment;

import javax.validation.constraints.NotNull;

import com.turkcell.rentAcar.business.requests.creditCard.CreateCreditCardRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	@NotNull
	private int invoiceId;
	
	@NotNull
	private int orderedAdditionalServiceId;
	
	@NotNull
	private CreateCreditCardRequest createCreditCardRequest;
}
