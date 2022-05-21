package com.turkcell.rentAcar.business.requests.carMaintenance;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {
	@NotNull
	@Size(min=2)
	private String description;
	@Positive
	@NotNull
	private int carId;
}
