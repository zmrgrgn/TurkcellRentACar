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

import com.turkcell.rentAcar.business.abstracts.CarService;
import com.turkcell.rentAcar.business.dtos.car.GetCarDto;
import com.turkcell.rentAcar.business.dtos.car.ListCarDto;
import com.turkcell.rentAcar.business.requests.car.CreateCarRequest;
import com.turkcell.rentAcar.business.requests.car.DeleteCarRequest;
import com.turkcell.rentAcar.business.requests.car.UpdateCarRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {
	private CarService carService;
	@Autowired
	public CarsController(CarService carService) {
		super();
		this.carService = carService;
	}
	@GetMapping("/getall")
	 public DataResult<List<ListCarDto>> getAll(){
		 return this.carService.getAll();
	 }
	@PostMapping("add")
	 public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) throws BusinessException {
		return this.carService.add(createCarRequest);
	 }
	@GetMapping("/getid")
	public DataResult<GetCarDto> getById(@RequestParam("carId") int carId) throws BusinessException {
		return this.carService.getById(carId);
	}
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarRequest deleteCarRequest) throws BusinessException{
		return this.carService.delete(deleteCarRequest);
	}
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarRequest updateCarRequest)throws BusinessException{
		return this.carService.update(updateCarRequest);
	}
	@GetMapping("/getallPaged")
	public DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize){
		return this.carService.getAllPaged(pageNo, pageSize);
	}
	@GetMapping("/getallSorted")
	public DataResult<List<ListCarDto>> getAllSorted(){
		return this.carService.getAllSorted();
	}
	@GetMapping("/listbyprice")
	public DataResult<List<ListCarDto>> listByPriceLessThanEqual(int maxPrice) {
		return this.carService.listByPriceLessThanEqual(maxPrice);
	}
}

