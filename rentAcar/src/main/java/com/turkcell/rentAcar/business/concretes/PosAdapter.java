package com.turkcell.rentAcar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.FakeHalkBankPosService;
import com.turkcell.rentAcar.business.abstracts.FakeIsBankPosService;
import com.turkcell.rentAcar.business.abstracts.PaymentService;
import com.turkcell.rentAcar.business.abstracts.PosService;
import com.turkcell.rentAcar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessResult;
@Service
public class PosAdapter implements PosService {
	PaymentService paymentService;
	FakeHalkBankPosService fakeHalkBankPosService;
	FakeIsBankPosService fakeIsBankPosService;
	@Autowired
	public PosAdapter(@Lazy PaymentService paymentService, FakeHalkBankPosService fakeHalkBankPosService,FakeIsBankPosService fakeIsBankPosService) {
		this.paymentService = paymentService;
		this.fakeHalkBankPosService = fakeHalkBankPosService;
		this.fakeIsBankPosService = fakeIsBankPosService;
	}
	@Override
	public Result payment(CreatePaymentRequest createPaymentRequest) {
		String last4digits = createPaymentRequest.getCardNumber().substring(12);
		
		if(last4digits.equals("5002")) {
			fakeHalkBankPosService.fakeHalkBankService(createPaymentRequest.getCardCvvNumber(),
					createPaymentRequest.getCardOwnerName(), createPaymentRequest.getCardNumber());
			return new SuccessResult();
		}
		else if (last4digits.equals("5003")) {
			fakeIsBankPosService.fakeIsBankService(createPaymentRequest.getCardOwnerName(),
					createPaymentRequest.getCardNumber(), createPaymentRequest.getCardCvvNumber());
			return new SuccessResult();
		}
		else {
			throw new BusinessException("Ödeme başarısız.");
		}
	}
}
