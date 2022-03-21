package com.turkcell.rentAcar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.CustomerService;
import com.turkcell.rentAcar.business.abstracts.InvoiceService;
import com.turkcell.rentAcar.business.abstracts.RentalService;
import com.turkcell.rentAcar.business.dtos.customer.GetCustomerDto;
import com.turkcell.rentAcar.business.dtos.invoice.GetInvoiceDto;
import com.turkcell.rentAcar.business.dtos.invoice.ListInvoiceDto;
import com.turkcell.rentAcar.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentAcar.business.requests.invoice.DeleteInvoiceRequest;
import com.turkcell.rentAcar.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.ErrorDataResult;
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
	private RentalService rentalService;
	
	@Autowired
	public InvoiceManager(ModelMapperService modelMapperService, InvoiceDao invoiceDao,CustomerService customerService,RentalService rentalService) {
		this.modelMapperService = modelMapperService;
		this.invoiceDao = invoiceDao;
		this.customerService=customerService;
		this.rentalService=rentalService;
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getAll() {
		
		var result = this.invoiceDao.findAll();
		
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class)).collect(Collectors.toList());
		response=toSetReturnDateForGetAllMethod(result,response);
		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest){
		
		checkRentalIdExist(createInvoiceRequest.getRentalId());
		
		checkCustomerIdExist(createInvoiceRequest.getCustomerId());
		
		checkInvoiceNoExist(createInvoiceRequest.getInvoiceNo());
		
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		toSetForAddMethod(invoice,createInvoiceRequest.getRentalId(),createInvoiceRequest.getCustomerId());
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice.Added");
	}

	@Override
	public DataResult<GetInvoiceDto> getByInvoiceId(int id){
		
		Invoice result = this.invoiceDao.getInvoiceById(id);
		
		if (result == null) {
			
			return new ErrorDataResult<GetInvoiceDto>("Böyle bir id bulunamadı.");
		}
		GetInvoiceDto response = this.modelMapperService.forDto().map(result, GetInvoiceDto.class);
		return new SuccessDataResult<GetInvoiceDto>(response);
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		
		Invoice invoice = this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);
		
		checkInvoiceIdExist(invoice);
		
		this.invoiceDao.deleteById(invoice.getId());
		return new SuccessResult("invoice.Deleted");
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		
		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
		
		checkInvoiceIdExist(invoice);
			
		this.invoiceDao.save(invoice);
		return new SuccessResult("invoice.Updated");
	
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByDateOfBetween(LocalDate startDate, LocalDate finishDate) {
		
		var result = this.invoiceDao.findByCreateDateBetween(startDate, finishDate);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id) {
		
		checkCustomerIdExist(id);
		
		var result = this.invoiceDao.getAllByCustomerId(id);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}
	
	private boolean checkInvoiceIdExist(Invoice invoice) {
		
		if(this.invoiceDao.getInvoiceById(invoice.getId()) != null) {
			
			return true;
		}
		throw new BusinessException("Böyle bir id bulunmamaktadır.");
	}
	
	private boolean checkInvoiceNoExist(long invoiceNo) {
		
		if(this.invoiceDao.getInvoiceByInvoiceNo(invoiceNo) == null) {
			
			return true;
		}
		throw new BusinessException("Böyle bir fatura numarası var.");
	}
	
	private boolean checkCustomerIdExist(int customerId) {
		
		if(this.customerService.getById(customerId).getData() != null) {
			
			return true;
		}
		throw new BusinessException("Böyle bir customer id'si bulunmamaktadır.");
	}
	
	private boolean checkRentalIdExist(int rentalId) {
		
		if(this.rentalService.getById(rentalId).getData() != null) {
			
			return true;
		}
		throw new BusinessException("Böyle bir rental id'si bulunmamaktadır.");
	}
	
	private Invoice toSetForAddMethod(Invoice invoice, int rentalId, int userId) {
		
		var rental=this.rentalService.getById(rentalId).getData();
		invoice.setRentDate(rental.getRentDate());
		invoice.setReturnDate(rental.getReturnDate());
		invoice.setTotalDay(ChronoUnit.DAYS.between(rental.getRentDate(),rental.getReturnDate())+1);
		invoice.setRentTotalPrice(rental.getTotalPrice());

		GetCustomerDto getCustomerDto=this.customerService.getById(invoice.getCustomer().getCustomerId()).getData();
		Customer customer=this.modelMapperService.forDto().map(getCustomerDto, Customer.class);
		
		invoice.setCustomer(customer);
		
		return invoice;
	}
	private List<ListInvoiceDto> toSetReturnDateForGetAllMethod(List<Invoice> result, List<ListInvoiceDto> response){
		for(int i=0 ; i<response.size() ; i++) {
			response.get(i).setRentDate(result.get(i).getReturnDate());
			response.get(i).setRentalId(result.get(i).getRental().getId());
		}
		return response;
	}
}
