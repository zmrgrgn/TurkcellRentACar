package com.turkcell.rentAcar.business.adapters;

import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.PosService;
import com.turkcell.rentAcar.business.outServices.FakeIsBankManager;
import com.turkcell.rentAcar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentAcar.core.results.Result;

@Service

public class FakeIsBankServiceAdapter implements PosService{

	@Override
	public Result payment(CreatePaymentRequest createPaymentRequest) {
		FakeIsBankManager fakeIsBankManager=new FakeIsBankManager();
		return fakeIsBankManager.makePayment( createPaymentRequest.getCardOwnerName(), createPaymentRequest.getCardNumber(),createPaymentRequest.getCardCvvNumber());
		
	}

}
