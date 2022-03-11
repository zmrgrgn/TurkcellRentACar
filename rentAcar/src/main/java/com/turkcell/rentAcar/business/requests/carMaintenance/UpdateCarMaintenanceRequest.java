package com.turkcell.rentAcar.business.requests.carMaintenance;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarMaintenanceRequest {
	@NotNull
	@Positive
	private int id;
	private String description;
	private LocalDate returnDate;
	@Positive
	private int carId;
}
