package com.turkcell.rentAcar.business.requests.rental;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {
	@NotNull
	private LocalDate rentDate;
	@NotNull
	private LocalDate returnDate;
	@NotNull
	private int customerId;
	@NotNull
	private int carId;
	private int rentCityId;
	private int returnCityId;
}
