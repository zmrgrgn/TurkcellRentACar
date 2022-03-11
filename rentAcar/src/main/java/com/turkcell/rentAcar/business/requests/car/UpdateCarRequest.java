package com.turkcell.rentAcar.business.requests.car;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	@Positive
	@NotNull
	private int id;
	@Positive
	private int dailyPrice;
	@Positive
	private int modelYear;
	private String description;
	@Positive
	private int brandId;
	@Positive
	private int colorId;
}
