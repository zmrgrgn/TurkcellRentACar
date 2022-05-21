package com.turkcell.rentAcar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentAcar.entities.concretes.CarMaintenance;

@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer>{
	CarMaintenance getCarMaintenanceById(int carMaintenanceId);
	List<CarMaintenance> getAllByCarId (int id);
	CarMaintenance getByReturnDateAndCar_id(LocalDate returnDate, int carId);
}
