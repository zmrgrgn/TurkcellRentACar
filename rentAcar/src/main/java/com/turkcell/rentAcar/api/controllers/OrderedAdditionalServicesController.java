package com.turkcell.rentAcar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentAcar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentAcar.business.dtos.orderedadditionalservice.GetOrderedAdditionalServiceDto;
import com.turkcell.rentAcar.business.dtos.orderedadditionalservice.ListOrderedAdditionalServiceDto;
import com.turkcell.rentAcar.business.requests.orderedadditionalservice.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentAcar.business.requests.orderedadditionalservice.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentAcar.business.requests.orderedadditionalservice.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;
@RestController
@RequestMapping("/api/orderedadditionalservices")
public class OrderedAdditionalServicesController {
	
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	
	@Autowired
	public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
		super();
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll(){
		return this.orderedAdditionalServiceService.getAll();
	}
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException{
		return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
	}
	@GetMapping("/getid")
	public DataResult<GetOrderedAdditionalServiceDto> getById(@RequestParam("orderedAdditionalServiceId")int orderedAdditionalServiceId) throws BusinessException{
		return this.orderedAdditionalServiceService.getById(orderedAdditionalServiceId);
	}
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException{
		return this.orderedAdditionalServiceService.delete(deleteOrderedAdditionalServiceRequest);
	}
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException{
		return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
	}
	@GetMapping("/getrentalid")
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAllByRentalId(@RequestParam("rentalId")int rentalId){
		return this.orderedAdditionalServiceService.getAllByRentalId(rentalId);
	}
	@GetMapping("/getadditionalserviceid")
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAllByAdditionalServiceId(@RequestParam("additionalServiceId")int additionalServiceId){
		return this.orderedAdditionalServiceService.getAllByAdditionalServiceId(additionalServiceId);
	}
}
