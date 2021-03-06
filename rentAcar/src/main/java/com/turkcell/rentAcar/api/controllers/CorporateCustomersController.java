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

import com.turkcell.rentAcar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentAcar.business.dtos.corporatecustomer.GetCorporateCustomerDto;
import com.turkcell.rentAcar.business.dtos.corporatecustomer.ListCorporateCustomerDto;
import com.turkcell.rentAcar.business.requests.corporatecustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentAcar.business.requests.corporatecustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

@RestController
@RequestMapping("/api/corporatecustomers")
public class CorporateCustomersController {
	private CorporateCustomerService corporateCustomerService;
	@Autowired
	public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
		super();
		this.corporateCustomerService = corporateCustomerService;
	}
	@GetMapping("/getall")
	public DataResult<List<ListCorporateCustomerDto>> getAll(){
		return corporateCustomerService.getAll();
	}
	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest){
		return corporateCustomerService.add(createCorporateCustomerRequest);
	}
	@GetMapping("/getid")
	public DataResult<GetCorporateCustomerDto> getById(@RequestParam("id")int id){
		return corporateCustomerService.getById(id);
	}
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest){
		return corporateCustomerService.update(updateCorporateCustomerRequest);
	}
}
