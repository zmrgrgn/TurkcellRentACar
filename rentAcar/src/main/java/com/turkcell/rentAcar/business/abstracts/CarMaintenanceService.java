package com.turkcell.rentAcar.business.abstracts;

import java.util.List;

import com.turkcell.rentAcar.business.dtos.carMaintenance.GetCarMaintenanceDto;
import com.turkcell.rentAcar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.rentAcar.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentAcar.business.requests.carMaintenance.DeleteCarMaintenanceRequest;
import com.turkcell.rentAcar.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface CarMaintenanceService {
	
	DataResult<List<ListCarMaintenanceDto>> getAll();
	
	DataResult<List<ListCarMaintenanceDto>> getAllByCarId(int carId);

	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest);

	DataResult<GetCarMaintenanceDto> getById(int carMaintenanceId);

	Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest);

	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest);
}
