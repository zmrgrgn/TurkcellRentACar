package com.turkcell.rentAcar.business.dtos.corporatecustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCorporateCustomerDto {
	private int id;
	private String email;
	private String password;
	private String name;
	private int taxNumber;
}
