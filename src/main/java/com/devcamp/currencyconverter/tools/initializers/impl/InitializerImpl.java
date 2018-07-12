package com.devcamp.currencyconverter.tools.initializers.impl;

import com.devcamp.currencyconverter.cache.Cache;
import com.devcamp.currencyconverter.constants.Qualifiers;
import com.devcamp.currencyconverter.model.views.CurrencyView;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import com.devcamp.currencyconverter.services.api.RateLogService;
import com.devcamp.currencyconverter.services.api.RateService;
import com.devcamp.currencyconverter.tools.initializers.api.Initializer;
import com.devcamp.currencyconverter.tools.mapper.api.Mapper;
import com.devcamp.currencyconverter.tools.scrapers.api.Scraper;
import com.devcamp.currencyconverter.tools.seeders.api.Seeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Component
public class InitializerImpl implements Initializer {

    private Seeder hotelSeeder;
    private Scraper currencyScraper;
    private Scraper countriesScraper;
    private Scraper locScraper;
    private Cache cache;
    private RateLogService rateLogService;
    private RateService rateService;
    private CurrencyService currencyService;
    private Mapper mapper;

    @Autowired
    public InitializerImpl(@Qualifier(Qualifiers.CURRENCY_SCRAPER) Scraper currencyScraper
            , @Qualifier(Qualifiers.COUNTRIES_SCRAPER) Scraper countriesScraper
            , @Qualifier(Qualifiers.LOC_SCRAPER) Scraper locScraper
            , @Qualifier(Qualifiers.HOTEL_SEEDER) Seeder hotelSeeder
            , @Qualifier(Qualifiers.MODEL_MAPPER) Mapper mapper
            , Cache cache, RateLogService rateLogService
            , RateService rateService
            , CurrencyService currencyService) {
        this.currencyScraper = currencyScraper;
        this.countriesScraper = countriesScraper;
        this.locScraper = locScraper;
        this.hotelSeeder = hotelSeeder;
        this.cache = cache;
        this.rateLogService = rateLogService;
        this.rateService = rateService;
        this.currencyService = currencyService;
        this.mapper = mapper;
    }


    @Override
    @PostConstruct
    public void initialize() throws IOException {
        this.currencyScraper.scrape();
        //this.countriesScraper.scrape();
        //this.hotelSeeder.seedData();
        this.locScraper.scrape();
        this.cache.cacheRatesTable(this.rateService.getTop8CurrenciesRates());
        this.cache.cacheCurrencies(Arrays.asList(this.mapper.convert
                (this.currencyService.findAll(), CurrencyView[].class)));
        this.rateLogService.logTop8Rates(this.cache.getTop8Rates());
        this.rateLogService.markIfRatesHaveDropped(this.cache.getTop8Rates());
    }
}
