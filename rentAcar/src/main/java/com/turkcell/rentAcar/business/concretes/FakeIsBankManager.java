package com.turkcell.rentAcar.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.FakeIsBankPosService;

@Service
public class FakeIsBankManager implements FakeIsBankPosService  {
	@Override
	public void fakeIsBankService(String fullName, String cardNo, int Cvv) {
		System.out.println("İş bank ile ödendi.");
	}
}
