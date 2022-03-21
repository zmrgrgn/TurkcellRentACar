package com.turkcell.rentAcar.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.FakeHalkBankPosService;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessResult;

@Service
public class FakeHalkBankManager implements FakeHalkBankPosService{
	@Override
	public Result fakeHalkBankService(int Cvv, String fullName, String cardNo) {
		System.out.println("Ödeme halk bankası ile yapılmıştır.");
		return new SuccessResult("Ödeme halk bankası ile yapılmıştır.");
	}
}
