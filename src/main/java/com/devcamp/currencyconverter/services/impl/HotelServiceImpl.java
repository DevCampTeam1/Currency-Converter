package com.devcamp.currencyconverter.services.impl;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Hotel;
import com.devcamp.currencyconverter.repositories.HotelRepository;
import com.devcamp.currencyconverter.services.api.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    private HotelRepository hotelRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }


    @Override
    public List<Hotel> findAllAvailableHotels(Double price, Currency currency){
        return this.hotelRepository.getAllAvailableHotels(price, currency.getId());
    }

    @Override
    public void save(Hotel hotel) {
        this.hotelRepository.saveAndFlush(hotel);
    }
}
