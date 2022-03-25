package com.turkcell.rentAcar.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.CustomerService;
import com.turkcell.rentAcar.business.constants.Messages;
import com.turkcell.rentAcar.business.dtos.customer.GetCustomerDto;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentAcar.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService{
	
	private ModelMapperService modelMapperService;
	private CustomerDao customerDao;
	
	public CustomerManager(ModelMapperService modelMapperService, CustomerDao customerDao) {
		this.modelMapperService = modelMapperService;
		this.customerDao = customerDao;
	}

	@Override
	public DataResult<GetCustomerDto> getById(int customerId){
		
		Customer result = this.customerDao.getById(customerId);
		
		if (result == null) {
			throw new BusinessException(Messages.CUSTOMERNOTFOUND);
		}
		GetCustomerDto response = this.modelMapperService.forDto().map(result, GetCustomerDto.class);
		//checkIfEmail(response.getEmail());
		return new SuccessDataResult<GetCustomerDto>(response, "Success");
	}
	
	private boolean checkIfEmail(String email) {
		
		if (this.customerDao.getCustomerByEmail(email) == null) {
		
			return true;
		}
		
		throw new BusinessException(Messages.CUSTOMERMAILEXISTS);
	}
}
