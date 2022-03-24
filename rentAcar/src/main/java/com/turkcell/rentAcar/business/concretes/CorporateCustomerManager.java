package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentAcar.business.constants.Messages;
import com.turkcell.rentAcar.business.dtos.corporatecustomer.GetCorporateCustomerDto;
import com.turkcell.rentAcar.business.dtos.corporatecustomer.ListCorporateCustomerDto;
import com.turkcell.rentAcar.business.requests.corporatecustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentAcar.business.requests.corporatecustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentAcar.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService{
	
	private ModelMapperService modelMapperService;
	private CorporateCustomerDao corporateCustomerDao;
	
	@Autowired
	public CorporateCustomerManager(ModelMapperService modelMapperService, CorporateCustomerDao corporateCustomerDao) {
		this.modelMapperService = modelMapperService;
		this.corporateCustomerDao = corporateCustomerDao;
	}

	@Override
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		
		var result = this.corporateCustomerDao.findAll();
		List<ListCorporateCustomerDto> response = result.stream().map(corporatecustomer -> this.modelMapperService.forDto().map(corporatecustomer, ListCorporateCustomerDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCorporateCustomerDto>>(response);
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest){
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult(Messages.CORPORATECUSTOMERADDED);
	}

	@Override
	public DataResult<GetCorporateCustomerDto> getById(int id){
		
		CorporateCustomer result = this.corporateCustomerDao.getById(id);
		
		if (result == null) {
			
			throw new BusinessException(Messages.CORPORATECUSTOMERNOTFOUND);
		}
		GetCorporateCustomerDto response = this.modelMapperService.forDto().map(result, GetCorporateCustomerDto.class);
		return new SuccessDataResult<GetCorporateCustomerDto>(response);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest){
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		
		checkCorporateCustomerIdExist(corporateCustomer);
		
		this.corporateCustomerDao.save(corporateCustomer);	
		return new SuccessResult(Messages.CORPORATECUSTOMERUPDATED);

	}
	
	private boolean checkCorporateCustomerIdExist(CorporateCustomer corporateCustomer) {
		
		if(this.corporateCustomerDao.getById(corporateCustomer.getCustomerId()) != null) {
		
			return true;
		}
		throw new BusinessException(Messages.CORPORATECUSTOMERNOTFOUND);
	}
	
}
