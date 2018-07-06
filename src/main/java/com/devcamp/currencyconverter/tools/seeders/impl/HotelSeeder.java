package com.devcamp.currencyconverter.tools.seeders.impl;

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

@Component
public class HotelSeeder implements Seeder {

    private static final String PATH = "/static/db-seed/hotels";

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
                String[] data = line.split(" - ");
                String countryName = data[0];
                String title = data[1];
                String pictureUrl = data[2];
                Double pricePerNight = Double.valueOf(data[3]);
                String url = data[4];
                Country country = this.countryService.findByName(countryName);
                Hotel hotel = new Hotel(title, pricePerNight, url, pictureUrl, country);
                this.hotelService.save(hotel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
