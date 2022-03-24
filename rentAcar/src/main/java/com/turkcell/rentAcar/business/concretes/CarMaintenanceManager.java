package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentAcar.business.abstracts.CarService;
import com.turkcell.rentAcar.business.abstracts.RentalService;
import com.turkcell.rentAcar.business.constants.Messages;
import com.turkcell.rentAcar.business.dtos.carMaintenance.GetCarMaintenanceDto;
import com.turkcell.rentAcar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.rentAcar.business.dtos.rental.ListRentalDto;
import com.turkcell.rentAcar.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentAcar.business.requests.carMaintenance.DeleteCarMaintenanceRequest;
import com.turkcell.rentAcar.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentAcar.entities.concretes.CarMaintenance;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {
	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private RentalService rentalService;

	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService,@Lazy CarService carService,@Lazy RentalService rentalService) {
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.carService=carService;
		this.rentalService=rentalService;
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getAll() {
		
		var result = this.carMaintenanceDao.findAll();
		
		List<ListCarMaintenanceDto> response = result.stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class)).collect(Collectors.toList());
		
		setCarIds(result, response);
		
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response);
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest){
		
		checkIfCarExists(createCarMaintenanceRequest.getCarId());
		
		checkCarMaintenance(createCarMaintenanceRequest.getCarId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,CarMaintenance.class);
		
		checkRentalCar(carMaintenance);
		
		carMaintenance.setId(0);
		
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult(Messages.CARMAINTENANCEADDED);
	}

	@Override
	public DataResult<GetCarMaintenanceDto> getById(int carMaintenanceId) {
		
		CarMaintenance result = this.carMaintenanceDao.getCarMaintenanceById(carMaintenanceId);
		if (result == null) {
			
			throw new BusinessException(Messages.CARMAINTENANCENOTFOUND);
		}
		
		GetCarMaintenanceDto response = this.modelMapperService.forDto().map(result, GetCarMaintenanceDto.class);
		return new SuccessDataResult<GetCarMaintenanceDto>(response);
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest){
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest,CarMaintenance.class);
		
		checkCarMaintenanceIdExist(carMaintenance);
		
		this.carMaintenanceDao.deleteById(carMaintenance.getId());
		
		return new SuccessResult(Messages.CARMAINTENANCEDELETED);
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest){
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,CarMaintenance.class);
		
		checkIfCarExists(updateCarMaintenanceRequest.getCarId());
		
		checkRentalCar(carMaintenance);
		
		checkCarMaintenanceIdExist(carMaintenance);
		
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult(Messages.CARMAINTENANCEUPDATED);

	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getAllByCarId(int carId) {
		
		List<CarMaintenance> result = this.carMaintenanceDao.getAllByCarId(carId);

		List<ListCarMaintenanceDto> response = result.stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response);
	}

	private boolean checkCarMaintenanceIdExist(CarMaintenance carMaintenance) {
		if(this.carMaintenanceDao.getCarMaintenanceById(carMaintenance.getId()) != null) {
			
			return true;
		}
		throw new BusinessException(Messages.CARMAINTENANCENOTFOUND);
	}
	private boolean checkIfCarExists(int carId) {
		
		if(this.carService.getById(carId).getData() != null) {
			
			return true;
		}
		throw new BusinessException(Messages.CARMAINTENANCECARIDNOTFOUND);
	}
	
	private void checkCarMaintenance(int carId) {
		
		List<CarMaintenance> carMaintenances=this.carMaintenanceDao.getAllByCarId(carId);
		
		for (CarMaintenance carMaintenance : carMaintenances) {
			
			if(carMaintenance.getReturnDate()==null) {
			
				throw new BusinessException(Messages.CARMAINTENANCESTILLMAINTENANCED);
			}
		}
	}
	
	private void checkRentalCar(CarMaintenance carMaintenance){
		
		DataResult<List<ListRentalDto>> result=this.rentalService.getAllByCarId(carMaintenance.getCar().getId());
			
		for (ListRentalDto rental : result.getData()) {
				
			if(rental.getReturnDate()==null) {
				
				throw new BusinessException(Messages.CARMAINTENANCECARINRENT);
			}
		}
	}
	private void setCarIds(List<CarMaintenance> result, List<ListCarMaintenanceDto> response){
		for(int i = 0; i < result.size(); i++) {
			response.get(i).setCarId(result.get(i).getCar().getId());
		}
	}

}
