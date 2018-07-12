package com.devcamp.currencyconverter.services.api;

import com.devcamp.currencyconverter.model.entities.Currency;
import com.devcamp.currencyconverter.model.entities.Hotel;
import com.devcamp.currencyconverter.model.views.HotelView;

import java.math.BigDecimal;
import java.util.List;

public interface HotelService {
    List<HotelView> findAllAvailableHotels(BigDecimal price, Currency currency);

    void save(Hotel hotel);
}
