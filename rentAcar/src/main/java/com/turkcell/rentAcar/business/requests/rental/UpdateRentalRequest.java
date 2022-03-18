package com.turkcell.rentAcar.business.requests.rental;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {
	@NotNull
	@Positive
	private int id;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private int returnKm;
    private int customerId;
	@NotNull
	private int carId;
	private int rentCityId;
	private int returnCityId;
}
