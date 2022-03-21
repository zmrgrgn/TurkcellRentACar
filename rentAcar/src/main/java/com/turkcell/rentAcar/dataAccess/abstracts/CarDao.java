package com.turkcell.rentAcar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentAcar.entities.concretes.Car;

@Repository
public interface CarDao extends JpaRepository<Car, Integer>{
	Car getCarById(int carId);
	List<Car> findByDailyPriceLessThanEqual(int dailyPrice);
}
