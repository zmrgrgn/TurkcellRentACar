package com.turkcell.rentAcar.business.outServices;

import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessResult;

@Service


public class FakeIsBankManager {

	public Result makePayment(String fullName, String cardNo, int Cvv) {
		System.out.println("Ödeme iş bankası ile yapılmıştır.");
		return new SuccessResult("Ödeme iş bankası ile yapılmıştır.");
	}
}
