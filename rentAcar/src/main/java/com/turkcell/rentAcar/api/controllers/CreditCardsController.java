package com.turkcell.rentAcar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentAcar.business.abstracts.CreditCardService;
import com.turkcell.rentAcar.business.dtos.creditCard.GetCreditCardDto;
import com.turkcell.rentAcar.business.dtos.creditCard.ListCreditCardDto;
import com.turkcell.rentAcar.business.requests.creditCard.CreateCreditCardRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

@RestController
@RequestMapping("/api/creditcards")
public class CreditCardsController {
	private CreditCardService creditCardService;
	@Autowired
	public CreditCardsController(CreditCardService creditCardService) {
		super();
		this.creditCardService = creditCardService;
	}
	@GetMapping("/getall")
	public DataResult<List<ListCreditCardDto>> getAll(){
		return creditCardService.getAll();
	}
	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateCreditCardRequest createCreditCardRequest) {
		return creditCardService.add(createCreditCardRequest);
	}
	@GetMapping("/getid")
	public DataResult<GetCreditCardDto> getById(@RequestParam("creditCardId") int creditCardId){
		return creditCardService.getById(creditCardId);
	}
}
