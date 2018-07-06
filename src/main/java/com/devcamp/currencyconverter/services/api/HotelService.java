package com.devcamp.currencyconverter.services.api;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Hotel;

import java.util.List;

public interface HotelService {
    List<Hotel> findAllAvailableHotels(Double price, Currency currency);
}
