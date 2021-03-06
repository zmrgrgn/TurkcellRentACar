package com.turkcell.rentAcar.business.dtos.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarDto {
	private int id;
	private int dailyPrice;
	private int modelYear;
	private String description;
	private int km;
	private String brandName;
	private String colorName;
}
