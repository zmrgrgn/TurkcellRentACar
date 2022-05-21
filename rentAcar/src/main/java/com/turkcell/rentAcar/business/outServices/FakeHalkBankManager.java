package com.turkcell.rentAcar.business.outServices;

import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessResult;

@Service

public class FakeHalkBankManager {
	
	public Result odemeYap(int Cvv, String fullName, String cardNo) {
		System.out.println("Ödeme halk bankası ile yapılmıştır.");
		return new SuccessResult("Ödeme halk bankası ile yapılmıştır.");
	}
}
