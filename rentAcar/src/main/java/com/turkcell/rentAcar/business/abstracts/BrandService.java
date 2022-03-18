package com.turkcell.rentAcar.business.abstracts;

import java.util.List;

import com.turkcell.rentAcar.business.dtos.brand.GetBrandDto;
import com.turkcell.rentAcar.business.dtos.brand.ListBrandDto;
import com.turkcell.rentAcar.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentAcar.business.requests.brand.DeleteBrandRequest;
import com.turkcell.rentAcar.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface BrandService {
	DataResult<List<ListBrandDto>> getAll();

	Result add(CreateBrandRequest createBrandRequest) throws BusinessException;

	DataResult<GetBrandDto> getById(int brandId) throws BusinessException;

	Result delete(DeleteBrandRequest deleteBrandRequest);

	Result update(UpdateBrandRequest updateBrandRequest);
}
