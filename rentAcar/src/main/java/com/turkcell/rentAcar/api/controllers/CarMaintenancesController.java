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

import com.turkcell.rentAcar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentAcar.business.dtos.carMaintenance.GetCarMaintenanceDto;
import com.turkcell.rentAcar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.rentAcar.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentAcar.business.requests.carMaintenance.DeleteCarMaintenanceRequest;
import com.turkcell.rentAcar.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

@RestController
@RequestMapping("/api/carmaintenances")
public class CarMaintenancesController {
	private CarMaintenanceService carMaintenanceService;
	@Autowired
	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
		super();
		this.carMaintenanceService = carMaintenanceService;
	}
	@GetMapping("/getall")
	 public DataResult<List<ListCarMaintenanceDto>> getAll(){
		 return this.carMaintenanceService.getAll();
	 }
	@PostMapping("/add")
	 public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createCarMaintenanceRequest){
		return this.carMaintenanceService.add(createCarMaintenanceRequest);
	 }
	@GetMapping("/getid")
	public DataResult<GetCarMaintenanceDto> getById(@RequestParam("carMaintenanceId") int carMaintenanceId){
		return this.carMaintenanceService.getById(carMaintenanceId);
	}
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarMaintenanceRequest deleteCarMaintenanceRequest){
		return this.carMaintenanceService.delete(deleteCarMaintenanceRequest);
	}
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest){
		return this.carMaintenanceService.update(updateCarMaintenanceRequest);
	}
	@GetMapping("/getcarid")
	public DataResult<List<ListCarMaintenanceDto>> getAllByCarId(@RequestParam("carId") int carId) {
		return carMaintenanceService.getAllByCarId(carId);
	}
}
