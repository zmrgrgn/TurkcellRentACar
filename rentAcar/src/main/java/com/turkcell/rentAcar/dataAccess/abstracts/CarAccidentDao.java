package com.turkcell.rentAcar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentAcar.entities.concretes.CarAccident;
@Repository
public interface CarAccidentDao extends JpaRepository<CarAccident, Integer> {
	CarAccident getCarAccidentById(int carAccidentId);
	List<CarAccident> getAllByCarId(int id);
}
