package com.devcamp.currencyconverter.tools.seeders.impl;

import com.devcamp.currencyconverter.constants.Qualifiers;
import com.devcamp.currencyconverter.entities.Country;
import com.devcamp.currencyconverter.entities.Hotel;
import com.devcamp.currencyconverter.services.api.CountryService;
import com.devcamp.currencyconverter.services.api.HotelService;
import com.devcamp.currencyconverter.tools.io.api.FileIO;
import com.devcamp.currencyconverter.tools.seeders.api.Seeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component(value = Qualifiers.HOTEL_SEEDER)
public class HotelSeeder implements Seeder {

    private static final String PATH = "/static/db-seed/hotels";
    private static final String SPLIT_DELIMITER = "/static/db-seed/hotels";

    private static final int COUNTRY_NAME_INDEX = 0;
    private static final int HOTEL_TITLE_INDEX = 1;
    private static final int PICTURE_URL_INDEX = 2;
    private static final int PRICE_INDEX = 3;
    private static final int URL_INDEX = 4;

    private HotelService hotelService;
    private CountryService countryService;
    private FileIO fileIO;

    @Autowired
    public HotelSeeder(HotelService hotelService, CountryService countryService, FileIO fileIO) {
        this.hotelService = hotelService;
        this.countryService = countryService;
        this.fileIO = fileIO;
    }

    @Override
    @PostConstruct
    public void seedData() {
        try {
            String text = this.fileIO.read(PATH);
            String[] lines = text.split(System.lineSeparator());
            for (String line : lines) {
                String[] data = line.split(SPLIT_DELIMITER);
                String countryName = data[COUNTRY_NAME_INDEX];
                String title = data[HOTEL_TITLE_INDEX];
                String pictureUrl = data[PICTURE_URL_INDEX];
                Double pricePerNight = Double.valueOf(data[PRICE_INDEX]);
                String url = data[URL_INDEX];
                Country country = this.countryService.findByName(countryName);
                Hotel hotel = new Hotel(title, pricePerNight, url, pictureUrl, country);
                this.hotelService.save(hotel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
