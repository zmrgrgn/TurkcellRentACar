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

import com.turkcell.rentAcar.business.abstracts.PaymentService;
import com.turkcell.rentAcar.business.dtos.payment.GetPaymentDto;
import com.turkcell.rentAcar.business.dtos.payment.ListPaymentDto;
import com.turkcell.rentAcar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
	
	PaymentService paymentService;

	@Autowired
	public PaymentsController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListPaymentDto>> getAll(){
		return paymentService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) {
		return paymentService.add(createPaymentRequest);
	}
	
	@GetMapping("/getid")
	public DataResult<GetPaymentDto> getById(@RequestParam("paymentId") int paymentId){
		return paymentService.getById(paymentId);
	}
}
