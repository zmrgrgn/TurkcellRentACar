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

import com.turkcell.rentAcar.business.abstracts.ColorService;
import com.turkcell.rentAcar.business.dtos.color.GetColorDto;
import com.turkcell.rentAcar.business.dtos.color.ListColorDto;
import com.turkcell.rentAcar.business.requests.color.CreateColorRequest;
import com.turkcell.rentAcar.business.requests.color.DeleteColorRequest;
import com.turkcell.rentAcar.business.requests.color.UpdateColorRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	private ColorService colorService;
	@Autowired
	public ColorsController(ColorService colorService) {
		super();
		this.colorService = colorService;
	}
	@GetMapping("/getall")
	 public DataResult<List<ListColorDto>> getAll(){
		 return this.colorService.getAll();
	 }
	@PostMapping("add")
	 public Result add(@RequestBody @Valid CreateColorRequest createColorRequest) throws BusinessException {
		 return this.colorService.add(createColorRequest);
	 }
	@GetMapping("/getid")
	public DataResult<GetColorDto> getById(@RequestParam("colorId") int colorId) throws BusinessException {
		return this.colorService.getById(colorId);
	}
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteColorRequest deleteColorRequest) throws BusinessException{
		return this.colorService.delete(deleteColorRequest);
	}
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateColorRequest updateColorRequest)throws BusinessException{
		return this.colorService.update(updateColorRequest);
	}
}
