package com.turkcell.rentAcar.business.abstracts;

import java.util.List;

import com.turkcell.rentAcar.business.dtos.caraccident.GetCarAccidentDto;
import com.turkcell.rentAcar.business.dtos.caraccident.ListCarAccidentDto;
import com.turkcell.rentAcar.business.requests.caraccident.CreateCarAccidentRequest;
import com.turkcell.rentAcar.business.requests.caraccident.DeleteCarAccidentRequest;
import com.turkcell.rentAcar.business.requests.caraccident.UpdateCarAccidentRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface CarAccidentService {
	
	DataResult<List<ListCarAccidentDto>> getAll();

	Result add(CreateCarAccidentRequest createCarAccidentRequest) ;

	DataResult<GetCarAccidentDto> getById(int id);
	
	DataResult<List<ListCarAccidentDto>> getAllByCarId(int carId);

	Result delete(DeleteCarAccidentRequest deleteCarAccidentRequest);

	Result update(UpdateCarAccidentRequest updateCarAccidentRequest);
	
}
