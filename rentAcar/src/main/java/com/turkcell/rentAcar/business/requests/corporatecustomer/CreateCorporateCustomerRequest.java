package com.turkcell.rentAcar.business.requests.corporatecustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporateCustomerRequest {
	private String email;
	private String password;
	private String name;
	private int taxNumber;
}
