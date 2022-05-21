package com.turkcell.rentAcar.business.requests.carMaintenance;

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
	@NotNull
	private String description;
	@NotNull
	private int carId;
}
