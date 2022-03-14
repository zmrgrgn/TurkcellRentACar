package com.turkcell.rentAcar.business.abstracts;

import java.util.List;

import com.turkcell.rentAcar.business.dtos.corporatecustomer.GetCorporateCustomerDto;
import com.turkcell.rentAcar.business.dtos.corporatecustomer.ListCorporateCustomerDto;
import com.turkcell.rentAcar.business.requests.corporatecustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentAcar.business.requests.corporatecustomer.DeleteCorporateCustomerRequest;
import com.turkcell.rentAcar.business.requests.corporatecustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface CorporateCustomerService {
	
	DataResult<List<ListCorporateCustomerDto>> getAll();

	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;

	DataResult<GetCorporateCustomerDto> getById(int corporateCustomerId) throws BusinessException;

	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException;

	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;

}
