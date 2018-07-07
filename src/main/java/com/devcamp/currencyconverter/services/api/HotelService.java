package com.devcamp.currencyconverter.services.api;

import com.devcamp.currencyconverter.model.entities.Currency;
import com.devcamp.currencyconverter.model.entities.Hotel;

import java.util.List;

public interface HotelService {
    List<Hotel> findAllAvailableHotels(Double price, Currency currency);

    void save(Hotel hotel);
}
