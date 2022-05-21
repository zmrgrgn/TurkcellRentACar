package com.turkcell.rentAcar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentAcar.entities.concretes.OrderedAdditionalService;

@Repository
public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService, Integer> {
	OrderedAdditionalService getOrderedAdditionalServiceById(int orderedAdditionalServiceId);
	List<OrderedAdditionalService> getAllByRentalId (int rentalId);
	List<OrderedAdditionalService> getAllByAdditionalServiceId (int additionalServiceId);
}
