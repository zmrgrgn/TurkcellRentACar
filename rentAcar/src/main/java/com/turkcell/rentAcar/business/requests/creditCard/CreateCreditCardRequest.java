package com.turkcell.rentAcar.business.requests.creditCard;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {
	
	private String cardOwnerName;
	private String cardNumber;
	private int cardCvvNumber;
	private LocalDate expirationDate;
}
