package com.devcamp.currencyconverter.repositories;

import com.devcamp.currencyconverter.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByPriceLessThanEqual(Double price);
}
