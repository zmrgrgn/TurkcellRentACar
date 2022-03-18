package com.turkcell.rentAcar.business.concretes;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.CarService;
import com.turkcell.rentAcar.business.abstracts.CustomerService;
import com.turkcell.rentAcar.business.abstracts.RentalService;
import com.turkcell.rentAcar.business.dtos.car.GetCarDto;
import com.turkcell.rentAcar.business.dtos.customer.GetCustomerDto;
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
import com.turkcell.rentAcar.entities.concretes.Customer;
import com.turkcell.rentAcar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {
	private RentalDao rentalDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceDao carMaintenanceDao;
	private CarService carService;
	private CustomerService customerService;

	@Autowired
	public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService, CarMaintenanceDao carMaintenanceDao, CarService carService,CustomerService customerService) {
		super();
		this.rentalDao = rentalDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceDao = carMaintenanceDao;
		this.carService=carService;
		this.customerService=customerService;
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
		rental.setAdditionalPrice(totalPriceCalculator(rental));
		rental.setCustomer(customerCorrection(createRentalRequest.getCustomerId()));
		
		rental.setRentKm(this.carService.getById(createRentalRequest.getCarId()).getData().getKm());

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
			
			rental.getCar().setKm(updateRentalRequest.getReturnKm());
			
			updateOperation(rental, updateRentalRequest);
			rental.setAdditionalPrice(totalPriceCalculator(rental));
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
	
	private double totalPriceCalculator(Rental rentalCar) throws BusinessException{
		
		GetCarDto car = this.carService.getById(rentalCar.getCar().getId()).getData();
		
		long dateBetween = ChronoUnit.DAYS.between(rentalCar.getRentDate(), rentalCar.getReturnDate());
		if(dateBetween==0) {
			dateBetween=1;
		}
		
		double rentPrice=car.getDailyPrice();
		double totalPrice=rentPrice*dateBetween;
 
        if(rentalCar.getRentCity().getId()!=rentalCar.getReturnCity().getId()) {
        	totalPrice=totalPrice+750;
        }
        
    		return totalPrice;
    
	}
	
	private void updateOperation(Rental rental, UpdateRentalRequest updateRentalRequest) {
		
		rental.setReturnDate(updateRentalRequest.getReturnDate());
		rental.setReturnKm(updateRentalRequest.getReturnKm());	
	}
	
	private Customer customerCorrection(int customerId) throws BusinessException{
		
		GetCustomerDto getCustomerDto = this.customerService.getById(customerId).getData();
		
		Customer customer = this.modelMapperService.forDto().map(getCustomerDto, Customer.class);
		
		return customer;
		
	}
}
