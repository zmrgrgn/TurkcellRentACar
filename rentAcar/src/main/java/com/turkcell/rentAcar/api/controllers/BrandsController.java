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

import com.turkcell.rentAcar.business.abstracts.BrandService;
import com.turkcell.rentAcar.business.dtos.brand.GetBrandDto;
import com.turkcell.rentAcar.business.dtos.brand.ListBrandDto;
import com.turkcell.rentAcar.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentAcar.business.requests.brand.DeleteBrandRequest;
import com.turkcell.rentAcar.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	private BrandService brandService;
	@Autowired
	public BrandsController(BrandService brandService) {
		super();
		this.brandService = brandService;
	}
	@GetMapping("/getall")
	public DataResult<List<ListBrandDto>> getAll(){
		return this.brandService.getAll();
	}
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest){
		return this.brandService.add(createBrandRequest);
	}
	@GetMapping("/getid")
	public DataResult<GetBrandDto> getById(@RequestParam("brandId") int brandId){
		return this.brandService.getById(brandId);
	}
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteBrandRequest deleteBrandRequest){
		return this.brandService.delete(deleteBrandRequest);
	}
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest){
		return this.brandService.update(updateBrandRequest);
	}
}
