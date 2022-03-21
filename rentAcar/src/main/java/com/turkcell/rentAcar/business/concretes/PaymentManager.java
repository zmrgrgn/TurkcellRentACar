package com.turkcell.rentAcar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentAcar.business.abstracts.PaymentService;
import com.turkcell.rentAcar.business.abstracts.PosService;
import com.turkcell.rentAcar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentAcar.entities.concretes.Payment;
@Service
public class PaymentManager implements PaymentService{
	PosService posService;
	ModelMapperService modelMapperService;
	PaymentDao paymentDao;
	
	@Autowired
	public PaymentManager(@Lazy PosService posService, ModelMapperService modelMapperService, PaymentDao paymentDao) {
		this.posService = posService;
		this.modelMapperService = modelMapperService;
		this.paymentDao = paymentDao;
	}

	@Override
	@Transactional
	public Result add(CreatePaymentRequest createPaymentRequest) {
		toSendPosService(createPaymentRequest);
	
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		
		checkIfOrderedAdditionalServiceId(createPaymentRequest.getOrderedAdditionalServiceId());
		
		this.paymentDao.save(payment);
		
		return new SuccessResult("Ödeme başarılı.");
		
	}
	
	private void toSendPosService(CreatePaymentRequest createPaymentRequest) {
		this.posService.payment(createPaymentRequest);
	}
	
	private boolean checkIfOrderedAdditionalServiceId(int orderedAdditionalServiceId) {
		
		if (this.paymentDao.getPaymentByOrderedAdditionalServiceId(orderedAdditionalServiceId) == null) {
		
			return true;
		}
		
		throw new BusinessException("Bu kiralamanın ödemesi yapılmıştır.");
	}

}
