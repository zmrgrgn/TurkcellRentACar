package com.turkcell.rentAcar.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentAcar.business.abstracts.InvoiceService;
import com.turkcell.rentAcar.business.dtos.invoice.GetInvoiceDto;
import com.turkcell.rentAcar.business.dtos.invoice.ListInvoiceDto;
import com.turkcell.rentAcar.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentAcar.business.requests.invoice.DeleteInvoiceRequest;
import com.turkcell.rentAcar.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {
	private InvoiceService invoiceService;
	@Autowired
	public InvoicesController(InvoiceService invoiceService) {
		super();
		this.invoiceService = invoiceService;
	}
	
	@PostMapping("/add")
	Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest){
		return this.invoiceService.add(createInvoiceRequest);
	}
	@DeleteMapping("/delete")
	Result delete(@RequestBody @Valid DeleteInvoiceRequest deleteInvoiceRequest){
		return this.invoiceService.delete(deleteInvoiceRequest);
	}
	@GetMapping("/getall")
	DataResult<List<ListInvoiceDto>> getAll(){
		return this.invoiceService.getAll();
	}
	@PutMapping("/update")
	Result update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest){
		return this.invoiceService.update(updateInvoiceRequest);
	}
	@GetMapping("/getbyid")
	DataResult<GetInvoiceDto> getByInvoiceId(@RequestParam("InvoiceId") int invoiceId){
		return this.invoiceService.getByInvoiceId(invoiceId);
	}
	@GetMapping("/getbydate")
	DataResult<List<ListInvoiceDto>> getByDateOfBetween (@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate finishDate){
		return this.invoiceService.getByDateOfBetween(startDate, finishDate);
	}
	@GetMapping("/getbycustomer")
	DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(@RequestParam int id) {
		return this.invoiceService.getInvoiceByCustomer(id);
	}
}
