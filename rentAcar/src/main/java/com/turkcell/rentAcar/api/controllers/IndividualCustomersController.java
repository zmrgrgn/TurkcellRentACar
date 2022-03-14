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

import com.turkcell.rentAcar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentAcar.business.dtos.individualcustomer.GetIndividualCustomerDto;
import com.turkcell.rentAcar.business.dtos.individualcustomer.ListIndividualCustomerDto;
import com.turkcell.rentAcar.business.requests.individualcustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentAcar.business.requests.individualcustomer.DeleteIndividualCustomerRequest;
import com.turkcell.rentAcar.business.requests.individualcustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

@RestController
@RequestMapping("/api/individualcustomers")
public class IndividualCustomersController {
	
	private IndividualCustomerService individualCustomerService;
	@Autowired
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		super();
		this.individualCustomerService = individualCustomerService;
	}
	@GetMapping("/getall")
	public DataResult<List<ListIndividualCustomerDto>> getAll(){
		return individualCustomerService.getAll();
	}
	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException{
		return individualCustomerService.add(createIndividualCustomerRequest);
	}
	@GetMapping("/getid")
	public DataResult<GetIndividualCustomerDto> getById(@RequestParam("individualCustomerId")int individualCustomerId) throws BusinessException{
		return individualCustomerService.getById(individualCustomerId);
	}
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException{
		return individualCustomerService.delete(deleteIndividualCustomerRequest);
	}
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException{
		return individualCustomerService.update(updateIndividualCustomerRequest);
	}
	

}
