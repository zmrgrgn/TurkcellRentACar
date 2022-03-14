package com.turkcell.rentAcar.business.abstracts;

import java.util.List;

import com.turkcell.rentAcar.business.dtos.individualcustomer.GetIndividualCustomerDto;
import com.turkcell.rentAcar.business.dtos.individualcustomer.ListIndividualCustomerDto;
import com.turkcell.rentAcar.business.requests.individualcustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentAcar.business.requests.individualcustomer.DeleteIndividualCustomerRequest;
import com.turkcell.rentAcar.business.requests.individualcustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface IndividualCustomerService {
	
	DataResult<List<ListIndividualCustomerDto>> getAll();

	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException;

	DataResult<GetIndividualCustomerDto> getById(int individualCustomerId) throws BusinessException;

	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException;

	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException;
}
