package com.turkcell.rentAcar.business.adapters;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.PosService;
import com.turkcell.rentAcar.business.outServices.FakeHalkBankManager;
import com.turkcell.rentAcar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentAcar.core.results.Result;
@Service
@Primary
public class FakeHalkBankServiceAdapter implements PosService{

	@Override
	public Result payment(CreatePaymentRequest createPaymentRequest) {
		FakeHalkBankManager fakeHalkBankManager=new FakeHalkBankManager();
		return fakeHalkBankManager.odemeYap(createPaymentRequest.getCardCvvNumber(), createPaymentRequest.getCardOwnerName(), createPaymentRequest.getCardNumber());
		
	}

}
