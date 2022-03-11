package com.turkcell.rentAcar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentAcar.entities.concretes.AdditionalService;

@Repository
public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer> {
	AdditionalService getAdditionalServiceById(int additionalServiceId);
	List<AdditionalService> getAllByRentalId (int id);
}
