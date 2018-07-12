package com.devcamp.currencyconverter.services.impl;

import com.devcamp.currencyconverter.constants.Qualifiers;
import com.devcamp.currencyconverter.model.entities.Currency;
import com.devcamp.currencyconverter.model.entities.Hotel;
import com.devcamp.currencyconverter.model.views.HotelView;
import com.devcamp.currencyconverter.repositories.HotelRepository;
import com.devcamp.currencyconverter.services.api.HotelService;
import com.devcamp.currencyconverter.tools.mapper.api.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    private HotelRepository hotelRepository;
    private Mapper mapper;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository
            , @Qualifier(Qualifiers.MODEL_MAPPER) Mapper mapper) {
        this.hotelRepository = hotelRepository;
        this.mapper = mapper;
    }


    @Override
    public List<HotelView> findAllAvailableHotels(BigDecimal price, Currency currency) {
        List<Hotel> availableHotels = this.hotelRepository.getAllAvailableHotels(price, currency.getId());
        HotelView[] hotelsView = this.mapper.convert(availableHotels, HotelView[].class);
        List<HotelView> hotels = Arrays.asList(hotelsView);
        hotels.forEach(h -> h.setNights(price));
        return hotels;
    }

    @Override
    public void save(Hotel hotel) {
        this.hotelRepository.saveAndFlush(hotel);
    }
}
