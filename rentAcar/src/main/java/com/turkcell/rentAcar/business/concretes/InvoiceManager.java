package com.turkcell.rentAcar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.CustomerService;
import com.turkcell.rentAcar.business.abstracts.InvoiceService;
import com.turkcell.rentAcar.business.abstracts.RentalService;
import com.turkcell.rentAcar.business.constants.Messages;
import com.turkcell.rentAcar.business.dtos.invoice.GetInvoiceDto;
import com.turkcell.rentAcar.business.dtos.invoice.ListInvoiceDto;
import com.turkcell.rentAcar.business.dtos.rental.GetRentalDto;
import com.turkcell.rentAcar.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentAcar.business.requests.invoice.DeleteInvoiceRequest;
import com.turkcell.rentAcar.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentAcar.entities.concretes.Invoice;
import com.turkcell.rentAcar.entities.concretes.Rental;

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
		
		List<Invoice> result = this.invoiceDao.findAll();
		
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}
	@Transactional
	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest){
		
		checkRentalIdExist(createInvoiceRequest.getRentalId());
		
		checkCustomerIdExist(createInvoiceRequest.getCustomerId());
		
		checkInvoiceNoExist(createInvoiceRequest.getInvoiceNo());
		
		GetRentalDto rentalDto=this.rentalService.getById(createInvoiceRequest.getRentalId()).getData();
		
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		
		toSetForAddMethod(rentalDto,invoice,createInvoiceRequest.getRentalId(),createInvoiceRequest.getCustomerId());
		
		this.invoiceDao.save(invoice);
		
		return new SuccessResult(Messages.INVOICEADDED);
	}

	@Override
	public DataResult<GetInvoiceDto> getByInvoiceId(int id){
		
		Invoice result = this.invoiceDao.getInvoiceById(id);
		
		if (result == null) {
			
			throw new BusinessException(Messages.INVOICENOTFOUND);
		}
		GetInvoiceDto response = this.modelMapperService.forDto().map(result, GetInvoiceDto.class);
		return new SuccessDataResult<GetInvoiceDto>(response);
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		
		Invoice invoice = this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);
		
		checkInvoiceIdExist(invoice);
		
		this.invoiceDao.deleteById(invoice.getId());
		return new SuccessResult(Messages.INVOICEDELETED);
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		
		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
		
		checkInvoiceIdExist(invoice);
			
		this.invoiceDao.save(invoice);
		return new SuccessResult(Messages.INVOICEUPDATED);
	
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByDateOfBetween(LocalDate startDate, LocalDate finishDate) {
		
		List<Invoice> result = this.invoiceDao.findByCreateDateBetween(startDate, finishDate);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, Messages.SUCCESS);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id) {
		
		checkCustomerIdExist(id);
		
		List<Invoice> result = this.invoiceDao.getAllByCustomerId(id);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, Messages.SUCCESS);
	}
	
	private boolean checkInvoiceIdExist(Invoice invoice) {
		
		if(this.invoiceDao.getInvoiceById(invoice.getId()) != null) {
			
			return true;
		}
		throw new BusinessException(Messages.INVOICENOTFOUND);
	}
	
	private boolean checkInvoiceNoExist(long invoiceNo) {
		
		if(this.invoiceDao.getInvoiceByInvoiceNo(invoiceNo) == null) {
			
			return true;
		}
		throw new BusinessException(Messages.INVOICENOEXISTS);
	}
	
	private boolean checkCustomerIdExist(int customerId) {
		
		if(this.customerService.getById(customerId).getData() != null) {
			
			return true;
		}
		throw new BusinessException(Messages.INVOICENOTFOUNDBYCUSTOMERID);
	}
	
	private boolean checkRentalIdExist(int rentalId) {
		
		if(this.rentalService.getById(rentalId).getData() != null) {
			
			return true;
		}
		throw new BusinessException(Messages.INVOICENOTFOUNDBYRENTALID);
	}
	
	private Invoice toSetForAddMethod(GetRentalDto getRentalDto ,Invoice invoice, int rentalId, int userId) {
		
		Rental rental=this.modelMapperService.forDto().map(getRentalDto, Rental.class);
		invoice.setRentDate(rental.getRentDate());
		invoice.setReturnDate(rental.getReturnDate());
		invoice.setTotalDay(ChronoUnit.DAYS.between(rental.getRentDate(),rental.getReturnDate())+1);
		invoice.setRentTotalPrice(rental.getTotalPrice());
		
		return invoice;
	}
}
