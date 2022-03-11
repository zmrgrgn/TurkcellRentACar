package com.turkcell.rentAcar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentAcar.entities.concretes.Brand;

@Repository
public interface BrandDao extends JpaRepository<Brand, Integer>{
	Brand getBrandById(int brandId);
	Brand getBrandByName(String brandName);
}
