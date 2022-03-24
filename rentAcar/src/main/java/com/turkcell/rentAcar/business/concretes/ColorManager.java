package com.turkcell.rentAcar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentAcar.business.abstracts.ColorService;
import com.turkcell.rentAcar.business.constants.Messages;
import com.turkcell.rentAcar.business.dtos.color.GetColorDto;
import com.turkcell.rentAcar.business.dtos.color.ListColorDto;
import com.turkcell.rentAcar.business.requests.color.CreateColorRequest;
import com.turkcell.rentAcar.business.requests.color.DeleteColorRequest;
import com.turkcell.rentAcar.business.requests.color.UpdateColorRequest;
import com.turkcell.rentAcar.core.exception.BusinessException;
import com.turkcell.rentAcar.core.results.DataResult;
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
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListColorDto>> getAll() {
		
		var result = this.colorDao.findAll();
		List<ListColorDto> response = result.stream().map(color -> this.modelMapperService.forDto().map(color, ListColorDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListColorDto>>(response);
	}

	@Override
	public Result add(CreateColorRequest createColorRequest){
		
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		
		checkIfColorName(createColorRequest);
		
		this.colorDao.save(color);
		return new SuccessResult(Messages.COLORADDED);
	}

	@Override
	public DataResult<GetColorDto> getById(int colorId){
		
		Color result = this.colorDao.getColorById(colorId);
		
		if (result == null) {
			
			throw new BusinessException(Messages.COLORNOTFOUND);
		}
		GetColorDto response = this.modelMapperService.forDto().map(result, GetColorDto.class);
		return new SuccessDataResult<GetColorDto>(response);
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest){
		
		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
		
		checkColorIdExist(color);
		
		this.colorDao.deleteById(color.getId());
		return new SuccessResult(Messages.COLORDELETED);
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest){
		
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		
		checkColorIdExist(color);
		
		this.colorDao.save(color);
		return new SuccessResult(Messages.COLORUPDATED);
	}

	private boolean checkColorIdExist(Color color) {
		
		if(this.colorDao.getColorById(color.getId()) != null) {
			
			return true;
		}
		throw new BusinessException(Messages.COLORNOTFOUND);
	}
	
	private boolean checkIfColorName(CreateColorRequest createColorRequest){
		
		Color color = this.colorDao.getColorByName(createColorRequest.getName());
		if (color == null) {
			
			return true;
		}
		throw new BusinessException(Messages.COLOREXISTS);
	}
}
