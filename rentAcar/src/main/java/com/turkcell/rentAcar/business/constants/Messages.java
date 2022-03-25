package com.turkcell.rentAcar.business.constants;

public class Messages {
	public static final String VALIDATIONERRORS = "Validation.Errors";
	public static final String BUSINESSEXCEPTIONERRORS = "BusinessException.Errors";
	public static final String SUCCESS = "Success";
	
	public static final String ADDITIONALSERVICEADDED = "AdditionalService.Added";
	public static final String ADDITIONALSERVICEUPDATED = "AdditionalService.Updated";
	public static final String ADDITIONALSERVICEDELETED = "AdditonalService.Deleted";
	public static final String ADDITIONALSERVICENOTFOUND = "Cannot find an additional service with this Id";
	
	public static final String BRANDADDED = "Brand.Added";
	public static final String BRANDUPDATED = "Brand.Updated";
	public static final String BRANDDELETED = "Brand.Deleted";
	public static final String BRANDNOTFOUND = "Cannot find a brand with this Id";
	public static final String BRANDEXISTS = "Such a brand exists";
	
	public static final String CARACCIDENTADDED = "CarAccident.Added";
	public static final String CARACCIDENTUPDATED = "CarAccident.Updated";
	public static final String CARACCIDENTDELETED = "CarAccident.Deleted";
	public static final String CARACCIDENTNOTFOUND = "Cannot find a car accident with this Id";
	public static final String CARACCIDENTCARIDNOTFOUND = "The car with this id does not car accident exist";
	
	public static final String CARMAINTENANCEADDED = "CarMaintenance.Added";
	public static final String CARMAINTENANCEUPDATED = "CarMaintenance.Updated";
	public static final String CARMAINTENANCEDELETED = "CarMaintenance.Deleted";
	public static final String CARMAINTENANCENOTFOUND = "Cannot find a car maintenance with this Id";
	public static final String CARMAINTENANCECARIDNOTFOUND = "The car with this id does not car maintenance exist";
	public static final String CARMAINTENANCECARINRENT = "The car cannot be sent for maintenance because it is on rent";
	public static final String CARMAINTENANCESTILLMAINTENANCED = "The car is still in maintenance";
	
	public static final String CARADDED = "Car.Added";
	public static final String CARUPDATED = "Car.Updated";
	public static final String CARDELETED = "Car.Deleted";
	public static final String CARNOTFOUND = "Cannot find a car with this Id";
	public static final String CARNOTFOUNDBYDAILYPRICE = "Cannot find a car which is daily price you wrote is below";
	public static final String CARNOTFOUNDBYBRANDID = "Cannot find a brand with this Id";
	public static final String CARNOTFOUNDBYCOLORID = "Cannot find a color with this Id";
	
	public static final String CITYADDED = "City.Added";
	public static final String CITYUPDATED = "City.Updated";
	public static final String CITYDELETED = "City.Deleted";
	public static final String CITYNOTFOUND = "Cannot find a city with this Id";
	
	public static final String COLORADDED = "Color.Added";
	public static final String COLORUPDATED = "Color.Updated";
	public static final String COLORDELETED = "Color.Deleted";
	public static final String COLOREXISTS = "Such a color exists";
	public static final String COLORNOTFOUND = "Cannot find a color with this Id";
	
	public static final String CORPORATECUSTOMERADDED = "CorporateCustomer.Added";
	public static final String CORPORATECUSTOMERUPDATED = "CorporateCustomer.Updated";
	public static final String CORPORATECUSTOMERNOTFOUND = "Cannot find a corporate customer with this Id";
	
	public static final String INDIVIDUALCUSTOMERADDED = "IndividualCustomer.Added";
	public static final String INDIVIDUALCUSTOMERUPDATED = "IndividualCustomer.Updated";
	public static final String INDIVIDUALCUSTOMERNOTFOUND = "Cannot find a individual customer with this Id";
	
	public static final String CUSTOMERNOTFOUND  = "Cannot find a customer with this Id";
	public static final String CUSTOMERMAILEXISTS = "Such a mail exists";
	
	public static final String INVOICEADDED = "Invoice.Added";
	public static final String INVOICEUPDATED = "Invoice.Updated";
	public static final String INVOICEDELETED = "Invoice.Deleted";
	public static final String INVOICENOTFOUND = "Cannot find a invoice with this Id";
	public static final String INVOICENOTFOUNDBYCUSTOMERID = "Cannot find a brand with this Id";
	public static final String INVOICENOTFOUNDBYRENTALID = "Cannot find a color with this Id";
	public static final String INVOICENOEXISTS = "Such a invoice no exists";
	
	public static final String ORDEREDADDITIONALSERVICEADDED = "OrderedAdditionalService.Added";
	public static final String ORDEREDADDITIONALSERVICEUPDATED = "OrderedAdditionalService.Updated";
	public static final String ORDEREDADDITIONALSERVICEDELETED = "OrderedAdditonalService.Deleted";
	public static final String ORDEREDADDITIONALSERVICENOTFOUND = "Cannot find an ordered additional service with this Id";
	public static final String ORDEREDADDITIONALSERVICERENTALIDNOTFOUND = "Cannot find a rented with this Id";
	public static final String ORDEREDADDITIONALSERVICEADDITIONALSERVİCEIDNOTFOUND = "Cannot find a additional service with this Id";
	
	public static final String RENTALADDED = "Rental.Added";
	public static final String RENTALUPDATED = "Rental.Updated";
	public static final String RENTALDELETED = "Rental.Deleted";
	public static final String RENTALNOTFOUND = "Cannot find a rental with this Id";
	public static final String RENTALINMAINTENANCE = "The car cannot be sent for rent because it is on maintenance";
	public static final String RENTALINRENT = "The car is still in rental";
	public static final String RENTALNOTFOUNDCAR = "The car with this id does not exist";
	public static final String RENTALNOTFOUNDCUSTOMER = "The customer with this id does not exist";
	
	public static final String PAYMENTADDED = "Payment.Added";
	public static final String PAYMENTNOTFOUND = "Cannot find a Payment with this Id";
	public static final String PAYMENTNOTFOUNDBYINVOICEID = "Cannot find a invoice with this Id";
	public static final String PAYMENTNOTFOUNDBYORDEREDADDITIONALSERVICEID = "Cannot find a ordered additional service with this Id";
	public static final String PAYMENTBYINVOICEEXISTS = "Such a invoice exists";
	public static final String PAYMENTBYORDEREDADDITIONALSERVICEEXISTS = "Such a ordered additional service exists";

	public static final String CREDITCARDADDED = "CreditCard.Added";
	public static final String CREDITCARDNOTFOUND = "Cannot find a credit card with this Id";
}
