package com.turkcell.rentAcar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentAcar.entities.concretes.Color;

@Repository
public interface ColorDao extends JpaRepository<Color, Integer> {
	Color getColorById(int colorId);
	Color getColorByName(String colorName);
}
