package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.CarAccidentService;
import com.turkcell.rentAcar.business.abstracts.CarService;
import com.turkcell.rentAcar.business.dtos.caraccident.GetCarAccidentDto;
import com.turkcell.rentAcar.business.dtos.caraccident.ListCarAccidentDto;
import com.turkcell.rentAcar.business.requests.caraccident.CreateCarAccidentRequest;
import com.turkcell.rentAcar.business.requests.caraccident.DeleteCarAccidentRequest;
import com.turkcell.rentAcar.business.requests.caraccident.UpdateCarAccidentRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.ErrorDataResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.CarAccidentDao;
import com.turkcell.rentAcar.entities.concretes.CarAccident;

@Service
public class CarAccidentManager implements CarAccidentService {
	
	ModelMapperService modelMapperService;
	CarAccidentDao carAccidentDao;
	CarService carService;
	
	@Autowired
	public CarAccidentManager(ModelMapperService modelMapperService, CarAccidentDao carAccidentDao,
			CarService carService) {
		this.modelMapperService = modelMapperService;
		this.carAccidentDao = carAccidentDao;
		this.carService = carService;
	}

	@Override
	public DataResult<List<ListCarAccidentDto>> getAll() {
		
		var result = this.carAccidentDao.findAll();
		List<ListCarAccidentDto> response = result.stream().map(carAccident -> this.modelMapperService.forDto().map(carAccident, ListCarAccidentDto.class)).collect(Collectors.toList());
		response = toGetAllSetCarId(result, response);
		return new SuccessDataResult<List<ListCarAccidentDto>>(response);
	}

	@Override
	public Result add(CreateCarAccidentRequest createCarAccidentRequest) {
		checkIfCarAccidentExistByCarId(createCarAccidentRequest.getCarId());//checkifcarexits
		
		CarAccident carAccident = this.modelMapperService.forRequest().map(createCarAccidentRequest, CarAccident.class);
		
		this.carAccidentDao.save(carAccident);
		
		return new SuccessResult("CarAccident.Added");
	}


	@Override
	public Result delete(DeleteCarAccidentRequest deleteCarAccidentRequest) {
		CarAccident carAccident = this.modelMapperService.forRequest().map(deleteCarAccidentRequest, CarAccident.class);
		
		checkCarAccidentIdExist(carAccident);
		
		this.carAccidentDao.deleteById(carAccident.getId());
		
		return new SuccessResult("carAccident.Deleted");
		
	}

	@Override
	public Result update(UpdateCarAccidentRequest updateCarAccidentRequest) {
		
		CarAccident carAccident = this.modelMapperService.forRequest().map(updateCarAccidentRequest, CarAccident.class);
		
		checkCarAccidentIdExist(carAccident);
		
		this.carAccidentDao.save(carAccident);
		
		return new SuccessResult("carAccident.Updated");
		
	}
	
	@Override
	public DataResult<GetCarAccidentDto> getById(int id) {
		CarAccident result = this.carAccidentDao.getById(id);
		if (result == null) {
		
			return new ErrorDataResult<GetCarAccidentDto>("Böyle bir id bulunamadı.");//business exceptiona çevir
		}
		
		GetCarAccidentDto response = this.modelMapperService.forDto().map(result, GetCarAccidentDto.class);
		return new SuccessDataResult<GetCarAccidentDto>(response);
	}
	
	@Override
	public DataResult<List<ListCarAccidentDto>> getAllByCarId(int carId) {
		checkIfCarAccidentExistByCarId(carId);
		
		var result = this.carAccidentDao.getAllByCarId(carId);

		List<ListCarAccidentDto> response = result.stream().map(carAccident -> this.modelMapperService.forDto().map(carAccident, ListCarAccidentDto.class)).collect(Collectors.toList());

		response =toGetAllSetCarId(result, response);
		
		return new SuccessDataResult<List<ListCarAccidentDto>>(response);
	}

	private boolean checkCarAccidentIdExist(CarAccident carAccident) {
		
		if(this.carAccidentDao.getCarAccidentById(carAccident.getId()) != null) {
		
			return true;
		}
		
		throw new BusinessException("Böyle bir id bulunmamaktadır.");

	}

	private boolean checkIfCarAccidentExistByCarId(int carId) {
		
		if(this.carService.getById(carId).getData() != null) {
		
			return true;
		}
		
		throw new BusinessException("Böyle bir araba id'si bulunmamaktadır.");
	}
	
	private List<ListCarAccidentDto> toGetAllSetCarId(List<CarAccident> result,List<ListCarAccidentDto> response) {
		for(int i = 0; i < result.size() ; i++) {
			response.get(i).setCarId(result.get(i).getCar().getId());		
		}
		return response;
	}

	

}
