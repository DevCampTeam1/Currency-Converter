package com.devcamp.currencyconverter.tools.initializers.impl;

import com.devcamp.currencyconverter.cache.Cache;
import com.devcamp.currencyconverter.constants.Qualifiers;
import com.devcamp.currencyconverter.tools.initializers.api.Initializer;
import com.devcamp.currencyconverter.tools.scrapers.api.Scraper;
import com.devcamp.currencyconverter.tools.seeders.api.Seeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class InitializerImpl implements Initializer {

    private Scraper currencyScraper;
    private Scraper countriesScraper;
    private Seeder hotelSeeder;
    private Cache cache;

    @Autowired
    public InitializerImpl(@Qualifier(value = Qualifiers.CURRENCY_SCRAPER) Scraper currencyScraper
            , @Qualifier(value = Qualifiers.COUNTRIES_SCRAPER) Scraper countriesScraper
            , @Qualifier(value = Qualifiers.HOTEL_SEEDER) Seeder hotelSeeder, Cache cache) {
        this.currencyScraper = currencyScraper;
        this.countriesScraper = countriesScraper;
        this.hotelSeeder = hotelSeeder;
        this.cache = cache;
    }


    @Override
    @PostConstruct
    public void initialize() throws IOException {
        //this.currencyScraper.scrape();
        //this.countriesScraper.scrape();
        //this.hotelSeeder.seedData();
        this.cache.initiate();
    }
}
