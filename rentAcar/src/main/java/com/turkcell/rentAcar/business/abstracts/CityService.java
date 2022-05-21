package com.turkcell.rentAcar.business.abstracts;

import java.util.List;

import com.turkcell.rentAcar.business.dtos.city.GetCityDto;
import com.turkcell.rentAcar.business.dtos.city.ListCityDto;
import com.turkcell.rentAcar.business.requests.city.CreateCityRequest;
import com.turkcell.rentAcar.business.requests.city.DeleteCityRequest;
import com.turkcell.rentAcar.business.requests.city.UpdateCityRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface CityService {
	
	DataResult<List<ListCityDto>> getAll();

	Result add(CreateCityRequest createCityRequest);

	DataResult<GetCityDto> getById(int cityId);

	Result delete(DeleteCityRequest deleteCityRequest);

	Result update(UpdateCityRequest updateCityRequest);
}
