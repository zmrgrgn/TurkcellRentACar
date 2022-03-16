package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentAcar.business.dtos.individualcustomer.GetIndividualCustomerDto;
import com.turkcell.rentAcar.business.dtos.individualcustomer.ListIndividualCustomerDto;
import com.turkcell.rentAcar.business.requests.individualcustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentAcar.business.requests.individualcustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.ErrorResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentAcar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService{
	
	private ModelMapperService modelMapperService;
	private IndividualCustomerDao individualCustomerDao;
	
	@Autowired
	public IndividualCustomerManager(ModelMapperService modelMapperService, IndividualCustomerDao individualCustomerDao) {
		this.modelMapperService = modelMapperService;
		this.individualCustomerDao = individualCustomerDao;
	}

	@Override
	public DataResult<List<ListIndividualCustomerDto>> getAll() {
		
		var result = this.individualCustomerDao.findAll();
		List<ListIndividualCustomerDto> response = result.stream()
				.map(individualcustomer -> this.modelMapperService.forDto().map(individualcustomer, ListIndividualCustomerDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListIndividualCustomerDto>>(response);
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult("individualCustomer.Added");
	}

	@Override
	public DataResult<GetIndividualCustomerDto> getById(int id) throws BusinessException {
		var result = this.individualCustomerDao.getById(id);
		if (result != null) {
			GetIndividualCustomerDto response = this.modelMapperService.forDto().map(result, GetIndividualCustomerDto.class);
			return new SuccessDataResult<GetIndividualCustomerDto>(response);
		}
		throw new BusinessException("Böyle bir id bulunmamaktadır.");
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);		
		if(checkIndividualCustomerIdExist(individualCustomer)) {
			this.individualCustomerDao.save(individualCustomer);
			return new SuccessResult("individualCustomer.Updated");
		}
		return new ErrorResult("individualCustomer.NotFound");
	}
	
	private boolean checkIndividualCustomerIdExist(IndividualCustomer individualCustomer) {

		return this.individualCustomerDao.getById(individualCustomer.getCustomerId()) != null;

	}

}
