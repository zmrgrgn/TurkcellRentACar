package com.turkcell.rentAcar.business.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPaymentDto {
	private int id;
	private int invoiceId;
	private int orderedAdditionalServiceId;
	private String carOwnerName;

}
