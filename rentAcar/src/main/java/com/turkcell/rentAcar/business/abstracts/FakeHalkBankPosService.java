package com.turkcell.rentAcar.business.abstracts;

import com.turkcell.rentAcar.core.results.Result;

public interface FakeHalkBankPosService {
	public Result fakeHalkBankService(int Cvv, String fullName, String cardNo);
}
