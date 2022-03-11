package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentAcar.business.dtos.carMaintenance.GetCarMaintenanceDto;
import com.turkcell.rentAcar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.rentAcar.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentAcar.business.requests.carMaintenance.DeleteCarMaintenanceRequest;
import com.turkcell.rentAcar.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.ErrorResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentAcar.dataAccess.abstracts.RentalDao;
import com.turkcell.rentAcar.entities.concretes.CarMaintenance;
import com.turkcell.rentAcar.entities.concretes.Rental;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {
	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private RentalDao rentalDao;

	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService,@Lazy RentalDao rentalDao) {
		super();
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.rentalDao=rentalDao;
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getAll() {
		var result = this.carMaintenanceDao.findAll();
		List<ListCarMaintenanceDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response);
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		checkRentalCar(createCarMaintenanceRequest.getCarId());
		checkCarMaintenance(createCarMaintenanceRequest.getCarId());
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
				CarMaintenance.class);
		this.carMaintenanceDao.save(carMaintenance);
		return new SuccessResult("CarMaintenance.Added");
	}

	@Override
	public DataResult<GetCarMaintenanceDto> getById(int carMaintenanceId) throws BusinessException {
		var result = this.carMaintenanceDao.getCarMaintenanceById(carMaintenanceId);
		if (result != null) {
			GetCarMaintenanceDto response = this.modelMapperService.forDto().map(result, GetCarMaintenanceDto.class);
			return new SuccessDataResult<GetCarMaintenanceDto>(response);
		}
		throw new BusinessException("Böyle bir id bulunmamaktadır.");
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest,
				CarMaintenance.class);
		if (checkCarMaintenanceIdExist(carMaintenance)) {
			this.carMaintenanceDao.deleteById(carMaintenance.getId());
			return new SuccessResult("CarMaintenance.Deleted");
		}
		return new ErrorResult("CarMaintenance.NotFound");

	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		checkRentalCar(updateCarMaintenanceRequest.getCarId());
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,
				CarMaintenance.class);
		if (checkCarMaintenanceIdExist(carMaintenance)) {
			this.carMaintenanceDao.save(carMaintenance);
			return new SuccessResult("CarMaintenance.Updated");
		}
		return new ErrorResult("CarMaintenance.NotFound");
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getAllByCarId(int carId) {
		List<CarMaintenance> result = this.carMaintenanceDao.getAllByCarId(carId);

		List<ListCarMaintenanceDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response);
	}

	private boolean checkCarMaintenanceIdExist(CarMaintenance carMaintenance) {
		return this.carMaintenanceDao.getCarMaintenanceById(carMaintenance.getId()) != null;
	}
	
	private void checkCarMaintenance(int carId) throws BusinessException {
		List<CarMaintenance> carMaintenances=this.carMaintenanceDao.getAllByCarId(carId);
		if(!carMaintenances.isEmpty()) {
			for (CarMaintenance carMaintenance : carMaintenances) {
				if(carMaintenance.getReturnDate()==null) {
					 throw new BusinessException("Araba bakımda, bakıma verilemez.");
				}
			}
		}
	}
	
	private void checkRentalCar(int carId) throws BusinessException {
		List<Rental> rentals=this.rentalDao.getAllByCarId(carId);
		if(!rentals.isEmpty()) {
			for (Rental rental : rentals) {
				if(rental.getReturnDate()==null) {
					 throw new BusinessException("Araba kirada, bakıma verilemez.");
				}
			}
		}
	}

}
