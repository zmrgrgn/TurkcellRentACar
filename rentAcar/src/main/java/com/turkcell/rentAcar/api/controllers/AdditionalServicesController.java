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

import com.turkcell.rentAcar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentAcar.business.dtos.additionalService.GetAdditionalServiceDto;
import com.turkcell.rentAcar.business.dtos.additionalService.ListAdditionalServiceDto;
import com.turkcell.rentAcar.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentAcar.business.requests.additionalService.DeleteAdditionalServiceRequest;
import com.turkcell.rentAcar.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

@RestController
@RequestMapping("/api/additionalservices")
public class AdditionalServicesController {
	private AdditionalServiceService additionalServiceService;
	@Autowired
	public AdditionalServicesController(AdditionalServiceService additionalServiceService) {
		super();
		this.additionalServiceService = additionalServiceService;
	}
	@GetMapping("/getall")
	public DataResult<List<ListAdditionalServiceDto>> getAll(){
		return this.additionalServiceService.getAll();
	}
	@PostMapping("/add")
	Result add(@RequestBody @Valid CreateAdditionalServiceRequest createAdditionalServiceRequest){
		return this.additionalServiceService.add(createAdditionalServiceRequest);
	}
	@GetMapping("/getid")
	DataResult<GetAdditionalServiceDto> getById(@RequestParam("additionalServiceId")int additionalServiceId) {
		return this.additionalServiceService.getById(additionalServiceId);
	}
	@DeleteMapping("/delete")
	Result delete(@RequestBody @Valid DeleteAdditionalServiceRequest deleteAdditionalServiceRequest){
		return this.additionalServiceService.delete(deleteAdditionalServiceRequest);
	}
	@PutMapping("/update")
	Result update(@RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest){
		return this.additionalServiceService.update(updateAdditionalServiceRequest);
	}
	

}
