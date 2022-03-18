package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.CarService;
import com.turkcell.rentAcar.business.abstracts.RentalService;
import com.turkcell.rentAcar.business.dtos.car.GetCarDto;
import com.turkcell.rentAcar.business.dtos.car.ListCarDto;
import com.turkcell.rentAcar.business.dtos.rental.ListRentalDto;
import com.turkcell.rentAcar.business.requests.car.CreateCarRequest;
import com.turkcell.rentAcar.business.requests.car.DeleteCarRequest;
import com.turkcell.rentAcar.business.requests.car.UpdateCarRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.ErrorResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.CarDao;
import com.turkcell.rentAcar.entities.concretes.Car;
import com.turkcell.rentAcar.entities.concretes.Rental;

@Service
public class CarManager implements CarService {
	private CarDao carDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
		super();
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCarDto>> getAll() {
		
		var result = this.carDao.findAll();
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
		
		return new SuccessResult("Car.Added");
	}

	@Override
	public DataResult<GetCarDto> getById(int carId) throws BusinessException {
		
		var result = this.carDao.getCarById(carId);
		if (result != null) {
			GetCarDto response = this.modelMapperService.forDto().map(result, GetCarDto.class);
			return new SuccessDataResult<GetCarDto>(response);
		}
		
		throw new BusinessException("Arabaların içerisinde böyle bir id bulunmamaktadır.");
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest){
		
		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
		if (checkCarIdExist(car)) {
			this.carDao.deleteById(car.getId());
			return new SuccessResult("Car.Deleted");
		}
		
		return new ErrorResult("Car.NotFound");
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {
		
		Car car=this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		if(checkCarIdExist(car)) {
			this.carDao.save(car);
			return new SuccessResult("Car.Updated");
		}
		
		return new ErrorResult("Car.NotFound");

	}
	@Override
	public DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize) {
		
		Pageable pageable=PageRequest.of(pageNo-1, pageSize);
		
		List<Car> result=this.carDao.findAll(pageable).getContent();
		
		List<ListCarDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllSorted() {
		
		Sort sort=Sort.by(Sort.Direction.ASC,"dailyPrice");
		
		List<Car> result=this.carDao.findAll(sort);
		
		List<ListCarDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response);
	}

	@Override
	public DataResult<List<ListCarDto>> listByPriceLessThanEqual(int maxPrice) {
		
		List<Car> result=this.carDao.findByDailyPriceLessThanEqual(maxPrice);
		
		List<ListCarDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response);
	}

	private boolean checkCarIdExist(Car car) {

		return this.carDao.getCarById(car.getId()) != null;

	}
}
