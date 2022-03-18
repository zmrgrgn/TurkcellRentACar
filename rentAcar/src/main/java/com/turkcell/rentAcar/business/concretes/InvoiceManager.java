package com.turkcell.rentAcar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.CustomerService;
import com.turkcell.rentAcar.business.abstracts.InvoiceService;
import com.turkcell.rentAcar.business.dtos.customer.GetCustomerDto;
import com.turkcell.rentAcar.business.dtos.invoice.GetInvoiceDto;
import com.turkcell.rentAcar.business.dtos.invoice.ListInvoiceDto;
import com.turkcell.rentAcar.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentAcar.business.requests.invoice.DeleteInvoiceRequest;
import com.turkcell.rentAcar.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.ErrorResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentAcar.entities.concretes.Customer;
import com.turkcell.rentAcar.entities.concretes.Invoice;

@Service
public class InvoiceManager implements InvoiceService{
	
	private ModelMapperService modelMapperService;
	private InvoiceDao invoiceDao;
	private CustomerService customerService;
	
	@Autowired
	public InvoiceManager(ModelMapperService modelMapperService, InvoiceDao invoiceDao,CustomerService customerService) {
		super();
		this.modelMapperService = modelMapperService;
		this.invoiceDao = invoiceDao;
		this.customerService=customerService;
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getAll() {
		var result = this.invoiceDao.findAll();
		List<ListInvoiceDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
		
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		
		GetCustomerDto customer = this.customerService.getById(createInvoiceRequest.getCustomerId()).getData();
		
		Customer c = this.modelMapperService.forDto().map(customer, Customer.class);
		
		invoice.setCustomer(c);
		
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice.Added");
	}

	@Override
	public DataResult<GetInvoiceDto> getByInvoiceId(int id) throws BusinessException {
		Invoice result = this.invoiceDao.getInvoiceById(id);
		if (result == null) {
			throw new BusinessException("Invoice.NotFound");
		}
		GetInvoiceDto response = this.modelMapperService.forDto().map(result, GetInvoiceDto.class);
		return new SuccessDataResult<GetInvoiceDto>(response);
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		Invoice invoice = this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);
		if (checkInvoiceIdExist(invoice)) {
			this.invoiceDao.deleteById(invoice.getId());
			return new SuccessResult("invoice.Deleted");
		}
		return new ErrorResult("invoice.NotFound");
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
		if(checkInvoiceIdExist(invoice)) {
			updateOperation(invoice,updateInvoiceRequest);
			this.invoiceDao.save(invoice);
			return new SuccessResult("invoice.Updated");
		}
		return new ErrorResult("invoice.NotFound");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByDateOfBetween(LocalDate startDate, LocalDate finishDate) {
		List<Invoice> result = this.invoiceDao.findByCreateDateBetween(startDate, finishDate);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id) {
		List<Invoice> result = this.invoiceDao.getByCustomer_customerId(id);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}
	
	private boolean checkInvoiceIdExist(Invoice invoice) {

		return this.invoiceDao.getInvoiceById(invoice.getId()) != null;

	}
	
	private void updateOperation(Invoice invoice, UpdateInvoiceRequest updateInvoiceRequest) {
		invoice.setCreateDate(updateInvoiceRequest.getCreateDate());
		
	}

}
