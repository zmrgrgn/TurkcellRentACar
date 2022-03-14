package com.turkcell.rentAcar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentAcar.entities.concretes.City;

public interface CityDao extends JpaRepository<City, Integer>{

}
