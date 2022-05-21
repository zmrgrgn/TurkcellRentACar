package com.turkcell.rentAcar.business.abstracts;

import java.util.List;

import com.turkcell.rentAcar.business.dtos.creditCard.GetCreditCardDto;
import com.turkcell.rentAcar.business.dtos.creditCard.ListCreditCardDto;
import com.turkcell.rentAcar.business.requests.creditCard.CreateCreditCardRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface CreditCardService {
	
	DataResult<List<ListCreditCardDto>> getAll();
	
	Result add(CreateCreditCardRequest createCreditCardRequest);

	DataResult<GetCreditCardDto> getById(int creditCardId);
}
