package com.turkcell.rentAcar.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.FakeHalkBankPosService;

@Service
public class FakeHalkBankManager implements FakeHalkBankPosService{
	@Override
	public void fakeHalkBankService(int Cvv, String fullName, String cardNo) {
		System.out.println("Halk bankası ile ödeme gerçekleşti.");
	}
}
