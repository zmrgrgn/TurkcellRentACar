package com.turkcell.rentAcar.business.abstracts;

import java.util.List;

import com.turkcell.rentAcar.business.dtos.orderedadditionalservice.GetOrderedAdditionalServiceDto;
import com.turkcell.rentAcar.business.dtos.orderedadditionalservice.ListOrderedAdditionalServiceDto;
import com.turkcell.rentAcar.business.requests.orderedadditionalservice.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentAcar.business.requests.orderedadditionalservice.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentAcar.business.requests.orderedadditionalservice.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface OrderedAdditionalServiceService {

	DataResult<List<ListOrderedAdditionalServiceDto>> getAll();

	Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest);

	DataResult<GetOrderedAdditionalServiceDto> getById(int orderedAdditionalServiceId);

	Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest);

	Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest);

	DataResult<List<ListOrderedAdditionalServiceDto>> getAllByRentalId(int rentalId);

	DataResult<List<ListOrderedAdditionalServiceDto>> getAllByAdditionalServiceId(int additionalServiceId);
}
