package com.turkcell.rentAcar.business.abstracts;

import com.turkcell.rentAcar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentAcar.core.results.Result;

public interface PosService {
	public Result payment(CreatePaymentRequest createPaymentRequest);
}