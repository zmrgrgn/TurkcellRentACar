package com.turkcell.rentAcar.business.dtos.rental;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRentalDto {
	private int id;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private int carId;
}
