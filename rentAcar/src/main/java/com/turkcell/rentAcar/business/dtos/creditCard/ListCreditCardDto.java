package com.turkcell.rentAcar.business.dtos.creditCard;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCreditCardDto {
	
	private int id;
	private String cardOwnerName;
	private String cardNumber;
	private int cardCvvNumber;
	private LocalDate expirationDate;
}
