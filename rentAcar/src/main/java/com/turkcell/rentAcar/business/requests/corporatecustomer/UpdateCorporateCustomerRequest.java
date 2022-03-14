package com.turkcell.rentAcar.business.requests.corporatecustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	
	private int id;
	private String mail;
	private String password;
	private String companyName;
	private String taxNumber;

}
