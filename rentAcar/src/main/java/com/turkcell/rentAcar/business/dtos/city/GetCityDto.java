package com.turkcell.rentAcar.business.dtos.city;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCityDto {
	private int id;
	private String name;
}
