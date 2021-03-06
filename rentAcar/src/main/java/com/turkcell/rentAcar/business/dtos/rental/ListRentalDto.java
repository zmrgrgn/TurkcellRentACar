package com.turkcell.rentAcar.business.dtos.rental;

import java.time.LocalDate;

import com.turkcell.rentAcar.business.dtos.city.GetCityDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListRentalDto {
	
	private int id;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private double totalPrice;
	private int rentKm;
	private int returnKm;
    private int customerId;
	private int carId;
	private GetCityDto rentCity;
	private GetCityDto returnCity;
}
