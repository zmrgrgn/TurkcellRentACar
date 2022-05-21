package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.BrandService;
import com.turkcell.rentAcar.business.constants.Messages;
import com.turkcell.rentAcar.business.dtos.brand.GetBrandDto;
import com.turkcell.rentAcar.business.dtos.brand.ListBrandDto;
import com.turkcell.rentAcar.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentAcar.business.requests.brand.DeleteBrandRequest;
import com.turkcell.rentAcar.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentAcar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {
	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListBrandDto>> getAll() {
		
		List<Brand> result = this.brandDao.findAll();
		List<ListBrandDto> response = result.stream().map(brand -> this.modelMapperService.forDto().map(brand, ListBrandDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListBrandDto>>(response);
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest){
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		
		checkIfBrandNameExist(createBrandRequest.getName());
			
		this.brandDao.save(brand);
		
		return new SuccessResult(Messages.BRANDADDED);
	}

	@Override
	public DataResult<GetBrandDto> getById(int brandId) {
		
		Brand result = this.brandDao.getBrandById(brandId);
		
		if (result == null) {
			
			throw new BusinessException(Messages.BRANDNOTFOUND);
		}
		GetBrandDto response = this.modelMapperService.forDto().map(result, GetBrandDto.class);
		return new SuccessDataResult<GetBrandDto>(response);
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest){
		
		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
		
		checkBrandIdExist(brand);
		
		this.brandDao.deleteById(brand.getId());
		
		return new SuccessResult(Messages.BRANDDELETED);
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest){
		
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		
		checkBrandIdExist(brand);
		
		checkIfBrandNameExist(updateBrandRequest.getName());
		
		this.brandDao.save(brand);
		
		return new SuccessResult(Messages.BRANDUPDATED);

	}

	private boolean checkIfBrandNameExist(String brandName) {
		
		if (this.brandDao.getBrandByName(brandName) == null) {
		
			return true;
		}
		
		throw new BusinessException(Messages.BRANDEXISTS);
	}

	private boolean checkBrandIdExist(Brand brand) {
		
		if(this.brandDao.getBrandById(brand.getId()) != null) {
		
			return true;
		
		}
		
		throw new BusinessException(Messages.BRANDNOTFOUND);

	}
}
