package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentAcar.business.dtos.additionalService.GetAdditionalServiceDto;
import com.turkcell.rentAcar.business.dtos.additionalService.ListAdditionalServiceDto;
import com.turkcell.rentAcar.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentAcar.business.requests.additionalService.DeleteAdditionalServiceRequest;
import com.turkcell.rentAcar.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.ErrorDataResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentAcar.entities.concretes.AdditionalService;

@Service
public class AdditionalServiceManager implements AdditionalServiceService{
	private ModelMapperService modelMapperService;
	private AdditionalServiceDao additionalServiceDao;
	
	@Autowired
	public AdditionalServiceManager(ModelMapperService modelMapperService, AdditionalServiceDao additionalServiceDao) {
		this.modelMapperService = modelMapperService;
		this.additionalServiceDao = additionalServiceDao;
	}

	@Override
	public DataResult<List<ListAdditionalServiceDto>> getAll() {
		
		var result = this.additionalServiceDao.findAll();
		List<ListAdditionalServiceDto> response = result.stream().map(additionalService -> this.modelMapperService.forDto().map(additionalService, ListAdditionalServiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListAdditionalServiceDto>>(response);
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		
		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest,AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult("additionalService.Added");
	}

	@Override
	public DataResult<GetAdditionalServiceDto> getById(int additionalServiceId){
		
		AdditionalService result = this.additionalServiceDao.getAdditionalServiceById(additionalServiceId);
		
		if (result == null) {
			return new ErrorDataResult<GetAdditionalServiceDto>("Böyle bir id bulunamadı.");
		}
		
		GetAdditionalServiceDto response = this.modelMapperService.forDto().map(result, GetAdditionalServiceDto.class);
		return new SuccessDataResult<GetAdditionalServiceDto>(response);
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		
		AdditionalService additionalService = this.modelMapperService.forRequest().map(deleteAdditionalServiceRequest,AdditionalService.class);

		checkAdditionalServiceIdExist(additionalService);

		this.additionalServiceDao.deleteById(additionalService.getId());

		return new SuccessResult("additionalService.Deleted");

	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest){
		
		AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest,AdditionalService.class);
			
		checkAdditionalServiceIdExist(additionalService);
		
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult("additionalService.Updated");

	}
	
	private boolean checkAdditionalServiceIdExist(AdditionalService additionalService) {
		
		if(this.additionalServiceDao.getAdditionalServiceById(additionalService.getId()) != null) {
			
			return true;
		}
		
		throw new BusinessException("Böyle bir id bulunmamaktadır.");
	}

}
