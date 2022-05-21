package com.turkcell.rentAcar.business.dtos.caraccident;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarAccidentDto {
	
	private int id;
	private String description;
	private int carId;
}
