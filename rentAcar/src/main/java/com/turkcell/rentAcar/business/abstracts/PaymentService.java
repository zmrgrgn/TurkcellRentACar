package com.turkcell.rentAcar.business.abstracts;

import java.util.List;

import com.turkcell.rentAcar.business.dtos.payment.GetPaymentDto;
import com.turkcell.rentAcar.business.dtos.payment.ListPaymentDto;
import com.turkcell.rentAcar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface PaymentService {
	public Result add(CreatePaymentRequest createPaymentRequest);
	DataResult<List<ListPaymentDto>> getAll();
	DataResult<GetPaymentDto> getById(int paymentId);
}
