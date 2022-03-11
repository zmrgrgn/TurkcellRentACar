package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.ColorService;
import com.turkcell.rentAcar.business.dtos.color.GetColorDto;
import com.turkcell.rentAcar.business.dtos.color.ListColorDto;
import com.turkcell.rentAcar.business.requests.color.CreateColorRequest;
import com.turkcell.rentAcar.business.requests.color.DeleteColorRequest;
import com.turkcell.rentAcar.business.requests.color.UpdateColorRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.ErrorResult;
import com.turkcell.rentAcar.core.results.Result;
import com.turkcell.rentAcar.core.results.SuccessDataResult;
import com.turkcell.rentAcar.core.results.SuccessResult;
import com.turkcell.rentAcar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentAcar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentAcar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		super();
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListColorDto>> getAll() {
		var result = this.colorDao.findAll();
		List<ListColorDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, ListColorDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListColorDto>>(response);
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		if (checkIfColorName(createColorRequest)) {
			this.colorDao.save(color);
			return new SuccessResult("Color.Added");
		}
		return new ErrorResult("Color.NotFound");
	}

	@Override
	public DataResult<GetColorDto> getById(int colorId) throws BusinessException {
		var result = this.colorDao.getColorById(colorId);
		if (result != null) {
			GetColorDto response = this.modelMapperService.forDto().map(result, GetColorDto.class);
			return new SuccessDataResult<GetColorDto>(response);
		}
		throw new BusinessException("Renklerin içerisinde böyle bir id bulunmamaktadır.");
	}

	private boolean checkIfColorName(CreateColorRequest createColorRequest) throws BusinessException {
		Color color = this.colorDao.getColorByName(createColorRequest.getName());
		if (color == null) {
			return true;
		}
		throw new BusinessException("Aynı isimde bir renk kayıtlıdır.");
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
		if (checkColorIdExist(color)) {
			this.colorDao.deleteById(color.getId());
			return new SuccessResult("Color.Deleted");
		}
		return new ErrorResult("Color.NotFound");
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		if (checkColorIdExist(color)) {
			this.colorDao.save(color);
			return new SuccessResult("Color.Updated");
		}
		return new ErrorResult("Color.NotFound");
	}

	private boolean checkColorIdExist(Color color) {

		return this.colorDao.getColorById(color.getId()) != null;

	}

}
