package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentAcar.business.abstracts.CreditCardService;
import com.turkcell.rentAcar.business.abstracts.InvoiceService;
import com.turkcell.rentAcar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentAcar.business.abstracts.PaymentService;
import com.turkcell.rentAcar.business.abstracts.PosService;
import com.turkcell.rentAcar.business.constants.Messages;
import com.turkcell.rentAcar.business.dtos.payment.GetPaymentDto;
import com.turkcell.rentAcar.business.dtos.payment.ListPaymentDto;
import com.turkcell.rentAcar.business.requests.creditCard.CreateCreditCardRequest;
import com.turkcell.rentAcar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentAcar.entities.concretes.Payment;
@Service
public class PaymentManager implements PaymentService{

	PosService posService;
	ModelMapperService modelMapperService;
	PaymentDao paymentDao;
	InvoiceService invoiceService;
	OrderedAdditionalServiceService orderedAdditionalServiceService;
	CreditCardService creditCardService;
	
	@Autowired
	public PaymentManager(PosService posService, ModelMapperService modelMapperService, PaymentDao paymentDao,InvoiceService invoiceService,OrderedAdditionalServiceService orderedAdditionalServiceService, CreditCardService creditCardService) {
		this.posService = posService;
		this.modelMapperService = modelMapperService;
		this.paymentDao = paymentDao;
		this.orderedAdditionalServiceService=orderedAdditionalServiceService;
		this.invoiceService=invoiceService;
		this.creditCardService=creditCardService;
	}

	@Override
	@Transactional
	public Result add(boolean rememberMe ,CreatePaymentRequest createPaymentRequest) {
		toSendPosService(createPaymentRequest);
	
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		
		checkIfOrderedAdditionalServiceId(createPaymentRequest.getOrderedAdditionalServiceId());
		
		checkIfInvoiceId(createPaymentRequest.getInvoiceId());
		
		checkOrderedAdditionalServiceIdExist(createPaymentRequest.getOrderedAdditionalServiceId());
		
		checkInvoiceIdExist(createPaymentRequest.getInvoiceId());
		
		saveCreditCard(rememberMe,createPaymentRequest.getCreateCreditCardRequest());
		
		this.paymentDao.save(payment);
		
		return new SuccessResult(Messages.PAYMENTADDED);
		
	}

	@Override
	public DataResult<List<ListPaymentDto>> getAll() {
		var result = this.paymentDao.findAll();
		
		List<ListPaymentDto> response = result.stream().map(payment -> this.modelMapperService.forDto().map(payment, ListPaymentDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListPaymentDto>>(response);
	}

	@Override
	public DataResult<GetPaymentDto> getById(int paymentId) {
		Payment result = this.paymentDao.getById(paymentId);
		if (result == null) {
			
			throw new BusinessException(Messages.PAYMENTNOTFOUND);
		}
		GetPaymentDto response = this.modelMapperService.forDto().map(result, GetPaymentDto.class);
		return new SuccessDataResult<GetPaymentDto>(response);
	}
	
	private void toSendPosService(CreatePaymentRequest createPaymentRequest) {
		
		this.posService.payment(createPaymentRequest);
	}
	
	private boolean checkIfOrderedAdditionalServiceId(int orderedAdditionalServiceId) {
		
		if (this.paymentDao.getPaymentByOrderedAdditionalServiceId(orderedAdditionalServiceId) == null) {
		
			return true;
		}
		
		throw new BusinessException(Messages.PAYMENTBYORDEREDADDITIONALSERVICEEXISTS);
	}
	private boolean checkIfInvoiceId(int invoiceId) {
		
		if (this.paymentDao.getPaymentByInvoiceId(invoiceId) == null) {
		
			return true;
		}
		
		throw new BusinessException(Messages.PAYMENTBYINVOICEEXISTS);
	}
	
	private boolean checkInvoiceIdExist(int invoiceId) {
		
		if(this.invoiceService.getByInvoiceId(invoiceId).getData() != null) {
		
			return true;
		}
		throw new BusinessException(Messages.PAYMENTNOTFOUNDBYINVOICEID);
	}
	
	private boolean checkOrderedAdditionalServiceIdExist(int orderedAdditionalService) {
	
		if(this.orderedAdditionalServiceService.getById(orderedAdditionalService).getData() != null) {
		
			return true;
		}
		throw new BusinessException(Messages.PAYMENTNOTFOUNDBYORDEREDADDITIONALSERVICEID);
	}
	
	private void saveCreditCard(boolean rememberMe, CreateCreditCardRequest createCreditCardRequest) {
		if(rememberMe) {
			creditCardService.add(createCreditCardRequest);
		}
	}
}
