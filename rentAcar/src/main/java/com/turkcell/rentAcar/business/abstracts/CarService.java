package com.turkcell.rentAcar.business.abstracts;

import java.util.List;

import com.turkcell.rentAcar.business.dtos.car.GetCarDto;
import com.turkcell.rentAcar.business.dtos.car.ListCarDto;
import com.turkcell.rentAcar.business.requests.car.CreateCarRequest;
import com.turkcell.rentAcar.business.requests.car.DeleteCarRequest;
import com.turkcell.rentAcar.business.requests.car.UpdateCarRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface CarService {
	DataResult<List<ListCarDto>> getAll();

	Result add(CreateCarRequest createCarRequest) throws BusinessException;

	DataResult<GetCarDto> getById(int carId) throws BusinessException;

	Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;

	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;

	DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<ListCarDto>> getAllSorted();

	DataResult<List<ListCarDto>> listByPriceLessThanEqual(int maxPrice);
	
}
