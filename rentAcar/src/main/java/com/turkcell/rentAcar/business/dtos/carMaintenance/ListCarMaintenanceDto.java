package com.turkcell.rentAcar.business.dtos.carMaintenance;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarMaintenanceDto {
	private int id;
	private String description;
	private LocalDate returnDate;
	private int carId;
}
