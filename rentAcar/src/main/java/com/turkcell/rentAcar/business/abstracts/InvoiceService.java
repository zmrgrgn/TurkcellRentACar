package com.turkcell.rentAcar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentAcar.business.dtos.invoice.GetInvoiceDto;
import com.turkcell.rentAcar.business.dtos.invoice.ListInvoiceDto;
import com.turkcell.rentAcar.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentAcar.business.requests.invoice.DeleteInvoiceRequest;
import com.turkcell.rentAcar.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentAcar.core.results.DataResult;
import com.turkcell.rentAcar.core.results.Result;

public interface InvoiceService {
	
	DataResult<List<ListInvoiceDto>> getAll();

	Result add(CreateInvoiceRequest createInvoiceRequest);

	DataResult<GetInvoiceDto> getByInvoiceId(int id);

	Result delete(DeleteInvoiceRequest deleteInvoiceRequest);

	Result update(UpdateInvoiceRequest updateInvoiceRequest);
	
	DataResult<List<ListInvoiceDto>> getByDateOfBetween (LocalDate startDate, LocalDate finishDate);
	
	DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id);
}
