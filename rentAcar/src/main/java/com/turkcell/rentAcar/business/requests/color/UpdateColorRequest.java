package com.turkcell.rentAcar.business.requests.color;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateColorRequest {
	@NotNull
	@Positive
	private int id;
	@NotNull
	private String name;
}
