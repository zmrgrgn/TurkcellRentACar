package com.turkcell.rentAcar.business.abstracts;

import com.turkcell.rentAcar.business.requests.payment.CreatePaymentRequest;

public interface PosService {
	public void payment(CreatePaymentRequest createPaymentRequest);
}