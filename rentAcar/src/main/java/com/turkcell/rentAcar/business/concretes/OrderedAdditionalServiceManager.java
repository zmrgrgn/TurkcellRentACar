package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentAcar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentAcar.business.abstracts.RentalService;
import com.turkcell.rentAcar.business.constants.Messages;
import com.turkcell.rentAcar.business.dtos.orderedadditionalservice.GetOrderedAdditionalServiceDto;
import com.turkcell.rentAcar.business.dtos.orderedadditionalservice.ListOrderedAdditionalServiceDto;
import com.turkcell.rentAcar.business.requests.orderedadditionalservice.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentAcar.business.requests.orderedadditionalservice.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentAcar.business.requests.orderedadditionalservice.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentAcar.entities.concretes.OrderedAdditionalService;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService{

	ModelMapperService modelMapperService;
	OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private RentalService rentalService;
	private AdditionalServiceService additionalServiceService;
	
	@Autowired
	public OrderedAdditionalServiceManager(ModelMapperService modelMapperService, OrderedAdditionalServiceDao orderedAdditionalServiceDao,RentalService rentalService,AdditionalServiceService additionalServiceService) {
		this.modelMapperService = modelMapperService;
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.additionalServiceService=additionalServiceService;
		this.rentalService=rentalService;
	}

	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {
		
		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.findAll();
		
		List<ListOrderedAdditionalServiceDto> response = result.stream().map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response);
	}

	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) {
		
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest,OrderedAdditionalService.class);
		
		checkAdditionalServiceIdExist(createOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		checkRentalIdExist(createOrderedAdditionalServiceRequest.getRentalId());
		
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		return new SuccessResult(Messages.ORDEREDADDITIONALSERVICEADDED);
	}

	@Override
	public DataResult<GetOrderedAdditionalServiceDto> getById(int orderedAdditionalServiceId){
		
		OrderedAdditionalService result = this.orderedAdditionalServiceDao.getOrderedAdditionalServiceById(orderedAdditionalServiceId);
		
		if (result == null) {
			
			throw new BusinessException(Messages.ORDEREDADDITIONALSERVICENOTFOUND);
		}
		GetOrderedAdditionalServiceDto response = this.modelMapperService.forDto().map(result, GetOrderedAdditionalServiceDto.class);
		return new SuccessDataResult<GetOrderedAdditionalServiceDto>(response);
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest){
		
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(deleteOrderedAdditionalServiceRequest,OrderedAdditionalService.class);
		
		checkOrderedAdditionalServiceIdExist(orderedAdditionalService);
		
		this.orderedAdditionalServiceDao.deleteById(orderedAdditionalService.getId());	
		return new SuccessResult(Messages.ORDEREDADDITIONALSERVICEDELETED);
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest){
		
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(updateOrderedAdditionalServiceRequest,OrderedAdditionalService.class);
		
		checkAdditionalServiceIdExist(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		checkRentalIdExist(updateOrderedAdditionalServiceRequest.getRentalId());
		
		checkOrderedAdditionalServiceIdExist(orderedAdditionalService);	
	
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		return new SuccessResult(Messages.ORDEREDADDITIONALSERVICEUPDATED);
	}

	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAllByRentalId(int rentalId) {
		
		checkRentalIdExist(rentalId);
		
		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.getAllByRentalId(rentalId);

		List<ListOrderedAdditionalServiceDto> response = result.stream().map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response);
	}

	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAllByAdditionalServiceId(int additionalServiceId) {
		
		checkAdditionalServiceIdExist(additionalServiceId);
		
		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.getAllByAdditionalServiceId(additionalServiceId);

		List<ListOrderedAdditionalServiceDto> response = result.stream().map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response);
	}
	
	private boolean checkOrderedAdditionalServiceIdExist(OrderedAdditionalService orderedAdditionalService) {
		
		if(this.orderedAdditionalServiceDao.getOrderedAdditionalServiceById(orderedAdditionalService.getId()) != null) {
		
			return true;
		}
		throw new BusinessException(Messages.ORDEREDADDITIONALSERVICENOTFOUND);
	}
	private boolean checkAdditionalServiceIdExist(int additionalServiceId) {
		
		if(this.additionalServiceService.getById(additionalServiceId).getData() != null) {
			
			return true;
		}
		throw new BusinessException(Messages.ORDEREDADDITIONALSERVICEADDITIONALSERVİCEIDNOTFOUND);
	}
	
	private boolean checkRentalIdExist(int rentalId) {
		
		if(this.rentalService.getById(rentalId).getData() != null) {
			
			return true;
		}
		throw new BusinessException(Messages.ORDEREDADDITIONALSERVICERENTALIDNOTFOUND);
	}
	

}
