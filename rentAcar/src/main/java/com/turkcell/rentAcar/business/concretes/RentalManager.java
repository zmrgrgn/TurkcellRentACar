package com.turkcell.rentAcar.business.concretes;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentAcar.business.abstracts.CarService;
import com.turkcell.rentAcar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentAcar.business.abstracts.RentalService;
import com.turkcell.rentAcar.business.dtos.orderedadditionalservice.ListOrderedAdditionalServiceDto;
import com.turkcell.rentAcar.business.dtos.rental.GetRentalDto;
import com.turkcell.rentAcar.business.dtos.rental.ListRentalDto;
import com.turkcell.rentAcar.business.requests.rental.CreateRentalRequest;
import com.turkcell.rentAcar.business.requests.rental.DeleteRentalRequest;
import com.turkcell.rentAcar.business.requests.rental.UpdateRentalRequest;
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
public class RentalManager implements RentalService {
	private RentalDao rentalDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceDao carMaintenanceDao;
	private AdditionalServiceService additionalServiceService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private CarService carService;

	@Autowired
	public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService, CarMaintenanceDao carMaintenanceDao, AdditionalServiceService additionalServiceService, OrderedAdditionalServiceService orderedAdditionalServiceService, CarService carService) {
		super();
		this.rentalDao = rentalDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceDao = carMaintenanceDao;
		this.additionalServiceService=additionalServiceService;
		this.orderedAdditionalServiceService=orderedAdditionalServiceService;
		this.carService=carService;
	}

	@Override
	public DataResult<List<ListRentalDto>> getAll() {
		
		var result = this.rentalDao.findAll();
		List<ListRentalDto> response = result.stream()
				.map(rental -> this.modelMapperService.forDto().map(rental, ListRentalDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListRentalDto>>(response);
	}

	@Override
	public DataResult<List<ListRentalDto>> getAllByCarId(int carId) {
		
		List<Rental> result = this.rentalDao.getAllByCarId(carId);

		List<ListRentalDto> response = result.stream()
				.map(rental -> this.modelMapperService.forDto().map(rental, ListRentalDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListRentalDto>>(response);
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) throws BusinessException {
		
		checkRentalCar(createRentalRequest.getCarId());
		
		checkCarMaintenance(createRentalRequest.getCarId());
		
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		

		this.rentalDao.save(rental);
		
		
		return new SuccessResult("Rental.Added");
	}

	@Override
	public DataResult<GetRentalDto> getById(int rentalId) throws BusinessException {
		
		var result = this.rentalDao.getRentalById(rentalId);
		if (result != null) {
			GetRentalDto response = this.modelMapperService.forDto().map(result, GetRentalDto.class);
			return new SuccessDataResult<GetRentalDto>(response);
		}
		
		throw new BusinessException("Böyle bir id bulunmamaktadır.");
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) throws BusinessException {

		Rental rental = this.modelMapperService.forRequest().map(deleteRentalRequest, Rental.class);
		if (checkRentalIdExist(rental)) {
			this.rentalDao.deleteById(rental.getId());
			return new SuccessResult("Rental.Deleted");
		}
		
		return new ErrorResult("Rental.NotFound");
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) throws BusinessException {

		checkCarMaintenance(updateRentalRequest.getCarId());
		
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		if (checkRentalIdExist(rental)) {
			rental.setAdditionalPrice(rentalCalculation(rental));
			this.rentalDao.save(rental);
			return new SuccessResult("Rental.Updated");
		}
	
		return new ErrorResult("Rental.NotFound");
	}

	private boolean checkRentalIdExist(Rental rental) {

		return this.rentalDao.getRentalById(rental.getId()) != null;
	}

	private void checkCarMaintenance(int carId) throws BusinessException {

		List<CarMaintenance> carMaintenances = this.carMaintenanceDao.getAllByCarId(carId);
		for (CarMaintenance carMaintenance : carMaintenances) {
			if (carMaintenance.getReturnDate() == null) {
				throw new BusinessException("Araba bakımda, kiraya verilemez.");
			}
		}
	}

	private void checkRentalCar(int carId) throws BusinessException {

		List<Rental> rentals = this.rentalDao.getAllByCarId(carId);
		for (Rental rental : rentals) {
			if (rental.getReturnDate() == null) {
				throw new BusinessException("Araba kirada, kiraya verilemez.");
			}
		}
	}
	
	private double rentalCalculation(Rental rental) throws BusinessException {
		
		double totalPrice = 0;
		
		List<ListOrderedAdditionalServiceDto> orderedAdditionalServiceDtos = orderedAdditionalServiceService.getAllByRentalId(rental.getId()).getData();
		
		if(orderedAdditionalServiceDtos.size() > 0) {
			for(ListOrderedAdditionalServiceDto orderedAdditionalServiceDto : orderedAdditionalServiceDtos) {
				totalPrice += additionalServiceService.getById(orderedAdditionalServiceDto.getAdditionalServiceId()).getData().getPrice(); 
			}	
		}
		
		if(rental.getRentCity().getId() != rental.getReturnCity().getId())
			totalPrice += 750;

		long days = ChronoUnit.DAYS.between(rental.getRentDate(), rental.getReturnDate());
		
		if(days == 0)
			days = 1;
		
		totalPrice += days * carService.getById(rental.getCar().getId()).getData().getDailyPrice();

		return totalPrice;
	}
}
