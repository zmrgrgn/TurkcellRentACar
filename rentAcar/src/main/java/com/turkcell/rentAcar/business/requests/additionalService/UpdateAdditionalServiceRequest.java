package com.turkcell.rentAcar.business.requests.additionalService;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalServiceRequest {
	@NotNull
	private int id;
	private String name;
	private int dailyPrice;
}
