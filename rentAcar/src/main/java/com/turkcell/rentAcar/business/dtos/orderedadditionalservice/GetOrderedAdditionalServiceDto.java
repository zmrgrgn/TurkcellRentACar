package com.turkcell.rentAcar.business.dtos.orderedadditionalservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderedAdditionalServiceDto {
	private int id;
	private int rentalId;
	private int additionalServiceId;
}
