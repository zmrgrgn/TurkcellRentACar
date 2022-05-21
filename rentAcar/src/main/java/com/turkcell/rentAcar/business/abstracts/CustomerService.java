package com.turkcell.rentAcar.business.abstracts;

import com.turkcell.rentAcar.business.dtos.customer.GetCustomerDto;
import com.turkcell.rentAcar.core.results.DataResult;

public interface CustomerService {
	DataResult<GetCustomerDto> getById(int customerId);
}
