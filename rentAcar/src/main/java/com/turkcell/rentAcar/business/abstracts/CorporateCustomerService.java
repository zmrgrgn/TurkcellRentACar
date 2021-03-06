package com.turkcell.rentAcar.business.abstracts;

import java.util.List;

import com.turkcell.rentAcar.business.dtos.corporatecustomer.GetCorporateCustomerDto;
import com.turkcell.rentAcar.business.dtos.corporatecustomer.ListCorporateCustomerDto;
import com.turkcell.rentAcar.business.requests.corporatecustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentAcar.business.requests.corporatecustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface CorporateCustomerService {
	
	DataResult<List<ListCorporateCustomerDto>> getAll();

	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);

	DataResult<GetCorporateCustomerDto> getById(int id);

	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

}
