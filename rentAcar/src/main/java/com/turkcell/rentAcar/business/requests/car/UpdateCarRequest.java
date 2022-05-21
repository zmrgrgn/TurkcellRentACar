package com.turkcell.rentAcar.business.requests.car;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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
	@Min(0)
	private int dailyPrice;
	@Positive
	@Min(0)
	private int modelYear;
	@NotNull
	@Size(min=2)
	private String description;
	@NotNull
	@Min(0)
	private int km;
	@Positive
	private int brandId;
	@Positive
	private int colorId;
}
