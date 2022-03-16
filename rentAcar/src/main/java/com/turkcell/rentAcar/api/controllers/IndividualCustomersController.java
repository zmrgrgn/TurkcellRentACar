package com.turkcell.rentAcar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	public DataResult<GetIndividualCustomerDto> getById(@RequestParam("id")int id) throws BusinessException{
		return individualCustomerService.getById(id);
	}
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException{
		return individualCustomerService.update(updateIndividualCustomerRequest);
	}
}
