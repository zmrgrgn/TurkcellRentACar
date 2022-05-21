package com.turkcell.rentAcar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentAcar.business.abstracts.CarAccidentService;
import com.turkcell.rentAcar.business.dtos.caraccident.GetCarAccidentDto;
import com.turkcell.rentAcar.business.dtos.caraccident.ListCarAccidentDto;
import com.turkcell.rentAcar.business.requests.caraccident.CreateCarAccidentRequest;
import com.turkcell.rentAcar.business.requests.caraccident.DeleteCarAccidentRequest;
import com.turkcell.rentAcar.business.requests.caraccident.UpdateCarAccidentRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

@RestController
@RequestMapping("/api/carAccidents")
public class CarAccidentsController {
	private CarAccidentService carAccidentService;

	public CarAccidentsController(CarAccidentService carAccidentService) {
		this.carAccidentService = carAccidentService;
	}
	
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarAccidentRequest createCarAccidentRequest) {
		return this.carAccidentService.add(createCarAccidentRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarAccidentRequest updateCarAccidentRequest) {
		return this.carAccidentService.update(updateCarAccidentRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarAccidentRequest deleteCarAccidentRequest) {
		return this.carAccidentService.delete(deleteCarAccidentRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListCarAccidentDto>> getAll() {
		return this.carAccidentService.getAll();
	}
	
	@GetMapping("/getcaraccidentbyid")
	public DataResult<GetCarAccidentDto> getById(@RequestParam @Valid int carAccidentId) {
		return this.carAccidentService.getById(carAccidentId);
	}
	
	@GetMapping("/getcaraccidentsbycarid")
	public DataResult<List<ListCarAccidentDto>> getAllByCarId(@RequestParam @Valid int carId){
		return this.carAccidentService.getAllByCarId(carId);
	}
}
