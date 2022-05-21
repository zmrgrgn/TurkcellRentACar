package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.CreditCardService;
import com.turkcell.rentAcar.business.constants.Messages;
import com.turkcell.rentAcar.business.dtos.creditCard.GetCreditCardDto;
import com.turkcell.rentAcar.business.dtos.creditCard.ListCreditCardDto;
import com.turkcell.rentAcar.business.requests.creditCard.CreateCreditCardRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.CreditCardDao;
import com.turkcell.rentAcar.entities.concretes.CreditCard;

@Service
public class CreditCardManager implements CreditCardService {
	
	private ModelMapperService modelMapperService;
	private CreditCardDao creditCardDao;

	@Autowired
	public CreditCardManager(ModelMapperService modelMapperService, CreditCardDao creditCardDao) {
		super();
		this.modelMapperService = modelMapperService;
		this.creditCardDao = creditCardDao;
	}

	@Override
	public DataResult<List<ListCreditCardDto>> getAll() {
		
		List<CreditCard> result = this.creditCardDao.findAll();
		
		List<ListCreditCardDto> response = result.stream().map(rental -> this.modelMapperService.forDto().map(rental, ListCreditCardDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCreditCardDto>>(response);
	}
	@Transactional
	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest) {
		
		CreditCard creditCard = this.modelMapperService.forRequest().map(createCreditCardRequest, CreditCard.class);
		
		this.creditCardDao.save(creditCard);
		
		return new SuccessResult(Messages.CREDITCARDADDED);
	}

	@Override
	public DataResult<GetCreditCardDto> getById(int creditCardId) {
		CreditCard result = this.creditCardDao.getCreditCardById(creditCardId);
		
		if (result == null) {
			
			throw new BusinessException(Messages.CREDITCARDNOTFOUND);
		}
		
		GetCreditCardDto response = this.modelMapperService.forDto().map(result, GetCreditCardDto.class);
		return new SuccessDataResult<GetCreditCardDto>(response);
	}

}
