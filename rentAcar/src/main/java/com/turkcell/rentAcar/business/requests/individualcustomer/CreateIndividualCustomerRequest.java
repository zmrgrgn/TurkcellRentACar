package com.turkcell.rentAcar.business.requests.individualcustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {
	
	private String mail;
	private String password;
	private String firstName;
	private String lastName;
}
