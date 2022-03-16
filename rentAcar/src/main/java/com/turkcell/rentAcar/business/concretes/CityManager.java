package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.CityService;
import com.turkcell.rentAcar.business.dtos.city.GetCityDto;
import com.turkcell.rentAcar.business.dtos.city.ListCityDto;
import com.turkcell.rentAcar.business.requests.city.CreateCityRequest;
import com.turkcell.rentAcar.business.requests.city.DeleteCityRequest;
import com.turkcell.rentAcar.business.requests.city.UpdateCityRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.ErrorResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.CityDao;
import com.turkcell.rentAcar.entities.concretes.City;

@Service
public class CityManager implements CityService{
	
	private ModelMapperService modelMapperService;
	private CityDao cityDao;
	
	@Autowired
	public CityManager(ModelMapperService modelMapperService, CityDao cityDao) {
		super();
		this.modelMapperService = modelMapperService;
		this.cityDao = cityDao;
	}

	@Override
	public DataResult<List<ListCityDto>> getAll() {
		var result = this.cityDao.findAll();
		List<ListCityDto> response = result.stream()
				.map(city -> this.modelMapperService.forDto().map(city, ListCityDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCityDto>>(response);
	}

	@Override
	public Result add(CreateCityRequest createCityRequest) throws BusinessException {
		City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
		this.cityDao.save(city);
		return new SuccessResult("City.Added");
	}

	@Override
	public DataResult<GetCityDto> getById(int cityId) throws BusinessException {
		var result = this.cityDao.getById(cityId);
		if (result != null) {
			GetCityDto response = this.modelMapperService.forDto().map(result, GetCityDto.class);
			return new SuccessDataResult<GetCityDto>(response);
		}
		throw new BusinessException("Böyle bir id bulunmamaktadır.");
	}

	@Override
	public Result delete(DeleteCityRequest deleteCityRequest) throws BusinessException {
		City city = this.modelMapperService.forRequest().map(deleteCityRequest, City.class);
		if (checkCityIdExist(city)) {
			this.cityDao.deleteById(city.getId());
			return new SuccessResult("City.Deleted");
		}
		return new ErrorResult("City.NotFound");
	}

	@Override
	public Result update(UpdateCityRequest updateCityRequest) throws BusinessException {
		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
		if(checkCityIdExist(city)) {
			this.cityDao.save(city);
			return new SuccessResult("city.Updated");
		}
		return new ErrorResult("city.NotFound");
	}
	private boolean checkCityIdExist(City city) {

		return this.cityDao.getById(city.getId()) != null;

	}

}
