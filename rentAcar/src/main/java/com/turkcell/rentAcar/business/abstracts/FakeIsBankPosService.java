package com.turkcell.rentAcar.business.abstracts;

import com.turkcell.rentAcar.core.results.Result;

public interface FakeIsBankPosService {
	public Result fakeIsBankService(String fullName, String cardNo, int Cvv);
}
