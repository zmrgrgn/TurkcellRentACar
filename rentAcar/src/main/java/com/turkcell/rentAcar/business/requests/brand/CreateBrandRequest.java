package com.turkcell.rentAcar.business.requests.brand;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBrandRequest {
	@NotNull
	@Size(min=2, max=50)
	private String name;
}
