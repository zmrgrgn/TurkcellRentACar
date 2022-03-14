package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentAcar.business.dtos.orderedadditionalservice.GetOrderedAdditionalServiceDto;
import com.turkcell.rentAcar.business.dtos.orderedadditionalservice.ListOrderedAdditionalServiceDto;
import com.turkcell.rentAcar.business.requests.orderedadditionalservice.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentAcar.business.requests.orderedadditionalservice.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentAcar.business.requests.orderedadditionalservice.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.ErrorResult;
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
	
	@Autowired
	public OrderedAdditionalServiceManager(ModelMapperService modelMapperService,
			OrderedAdditionalServiceDao orderedAdditionalServiceDao) {
		super();
		this.modelMapperService = modelMapperService;
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
	}

	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {
		var result = this.orderedAdditionalServiceDao.findAll();
		List<ListOrderedAdditionalServiceDto> response = result.stream().map(
				orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response);
	}

	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException {
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest,
				OrderedAdditionalService.class);
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		
		return new SuccessResult("OrderedAdditionalService.Added");
	}

	@Override
	public DataResult<GetOrderedAdditionalServiceDto> getById(int orderedAdditionalServiceId) throws BusinessException {
		var result = this.orderedAdditionalServiceDao.getOrderedAdditionalServiceById(orderedAdditionalServiceId);
		if (result != null) {
			GetOrderedAdditionalServiceDto response = this.modelMapperService.forDto().map(result, GetOrderedAdditionalServiceDto.class);
			return new SuccessDataResult<GetOrderedAdditionalServiceDto>(response);
		}
		
		throw new BusinessException("Böyle bir id bulunmamaktadır.");
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException {
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(deleteOrderedAdditionalServiceRequest,
				OrderedAdditionalService.class);
		if (checkOrderedAdditionalServiceIdExist(orderedAdditionalService)) {
			this.orderedAdditionalServiceDao.deleteById(orderedAdditionalService.getId());
			return new SuccessResult("OrderedAdditionalService.Deleted");
		}
		
		return new ErrorResult("OrderedAdditionalService.NotFound");
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException {
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(updateOrderedAdditionalServiceRequest,
				OrderedAdditionalService.class);
		if (checkOrderedAdditionalServiceIdExist(orderedAdditionalService)) {
			this.orderedAdditionalServiceDao.save(orderedAdditionalService);
			return new SuccessResult("orderedAdditionalService.Updated");
		}
		
		return new ErrorResult("additionalService.NotFound");
	}

	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAllByRentalId(int rentalId) {
		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.getAllByRentalId(rentalId);

		List<ListOrderedAdditionalServiceDto> response = result.stream().map(
				orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response);
	}

	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAllByAdditionalServiceId(int additionalServiceId) {
		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.getAllByAdditionalServiceId(additionalServiceId);

		List<ListOrderedAdditionalServiceDto> response = result.stream().map(
				orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response);
	}
	
	private boolean checkOrderedAdditionalServiceIdExist(OrderedAdditionalService orderedAdditionalService) {
		
		return this.orderedAdditionalServiceDao.getOrderedAdditionalServiceById(orderedAdditionalService.getId()) != null;
	}


}
