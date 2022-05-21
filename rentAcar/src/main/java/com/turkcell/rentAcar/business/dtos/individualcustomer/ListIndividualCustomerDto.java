package com.turkcell.rentAcar.business.dtos.individualcustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListIndividualCustomerDto {
	private int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private int identityNumber;
}
