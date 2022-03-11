package com.turkcell.rentAcar.business.abstracts;

import java.util.List;

import com.turkcell.rentAcar.business.dtos.color.GetColorDto;
import com.turkcell.rentAcar.business.dtos.color.ListColorDto;
import com.turkcell.rentAcar.business.requests.color.CreateColorRequest;
import com.turkcell.rentAcar.business.requests.color.DeleteColorRequest;
import com.turkcell.rentAcar.business.requests.color.UpdateColorRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface ColorService {
	DataResult<List<ListColorDto>> getAll();

	Result add(CreateColorRequest createColorRequest) throws BusinessException;

	DataResult<GetColorDto> getById(int colorId) throws BusinessException;

	Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException;

	Result update(UpdateColorRequest updateColorRequest) throws BusinessException;
}
