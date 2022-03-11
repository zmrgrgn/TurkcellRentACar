package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.BrandService;
import com.turkcell.rentAcar.business.dtos.brand.GetBrandDto;
import com.turkcell.rentAcar.business.dtos.brand.ListBrandDto;
import com.turkcell.rentAcar.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentAcar.business.requests.brand.DeleteBrandRequest;
import com.turkcell.rentAcar.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.ErrorResult;
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
		super();
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListBrandDto>> getAll() {
		
		var result = this.brandDao.findAll();
		List<ListBrandDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, ListBrandDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListBrandDto>>(response);
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		if (checkIfBrandName(createBrandRequest)) {
			this.brandDao.save(brand);
			return new SuccessResult("Brand.Added");
		}
		
		return new ErrorResult("Brand.NotFound");
	}

	@Override
	public DataResult<GetBrandDto> getById(int brandId) throws BusinessException {
		
		var result = this.brandDao.getBrandById(brandId);
		if (result != null) {
			GetBrandDto response = this.modelMapperService.forDto().map(result, GetBrandDto.class);
			return new SuccessDataResult<GetBrandDto>(response);
		}
		
		throw new BusinessException("Markaların içerisinde böyle bir id bulunmamaktadır.");
	}

	private boolean checkIfBrandName(CreateBrandRequest createBrandRequest) throws BusinessException {
		
		Brand brand = this.brandDao.getBrandByName(createBrandRequest.getName());
		if (brand == null) {
			return true;
		}
		
		throw new BusinessException("Aynı isimde bir marka bulunmaktadır.");
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException {
		
		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
		if (checkBrandIdExist(brand)) {
			this.brandDao.deleteById(brand.getId());
			return new SuccessResult("Brand.Deleted");
		}
		
		return new ErrorResult("Brand.NotFound");
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
		
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		if (checkBrandIdExist(brand)) {
			this.brandDao.save(brand);
			return new SuccessResult("Brand.Updated");
		}
		
		return new ErrorResult("Brand.NotFound");
	}

	private boolean checkBrandIdExist(Brand brand) {

		return this.brandDao.getBrandById(brand.getId()) != null;

	}
}
