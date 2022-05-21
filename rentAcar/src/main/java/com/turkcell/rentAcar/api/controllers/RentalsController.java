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

import com.turkcell.rentAcar.business.abstracts.RentalService;
import com.turkcell.rentAcar.business.dtos.rental.GetRentalDto;
import com.turkcell.rentAcar.business.dtos.rental.ListRentalDto;
import com.turkcell.rentAcar.business.requests.rental.CreateRentalRequest;
import com.turkcell.rentAcar.business.requests.rental.DeleteRentalRequest;
import com.turkcell.rentAcar.business.requests.rental.UpdateRentalRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {
	private RentalService rentalService;
	@Autowired
	public RentalsController(RentalService rentalService) {
		super();
		this.rentalService = rentalService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListRentalDto>> getAll(){
		return rentalService.getAll();
	}
	
	@GetMapping("/getcarid")
	public DataResult<List<ListRentalDto>> getAllByCarId(@RequestParam("carId") int carId){
		return rentalService.getAllByCarId(carId);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateRentalRequest createRentalRequest){
		return rentalService.add(createRentalRequest);
	}

	@GetMapping("/getid")
	public DataResult<GetRentalDto> getById(@RequestParam("rentalId") int rentalId){
		return rentalService.getById(rentalId);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteRentalRequest deleteRentalRequest){
		return rentalService.delete(deleteRentalRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestParam("id") int id,@RequestBody @Valid UpdateRentalRequest updateRentalRequest){
		return rentalService.update(id, updateRentalRequest);
	}
}
