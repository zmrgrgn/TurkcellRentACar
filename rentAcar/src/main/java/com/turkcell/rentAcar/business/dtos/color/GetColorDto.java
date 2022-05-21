package com.turkcell.rentAcar.business.dtos.color;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetColorDto {
	private int id;
	private String name;
}