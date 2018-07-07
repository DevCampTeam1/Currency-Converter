package com.devcamp.currencyconverter.tools.initializers.impl;

import com.devcamp.currencyconverter.cache.Cache;
import com.devcamp.currencyconverter.constants.Qualifiers;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import com.devcamp.currencyconverter.services.api.RateLogService;
import com.devcamp.currencyconverter.services.api.RateService;
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
    private Scraper locScraper;
    private Seeder hotelSeeder;
    private Cache cache;
    private RateLogService rateLogService;
    private RateService rateService;
    private CurrencyService currencyService;

    @Autowired
    public InitializerImpl(@Qualifier(value = Qualifiers.CURRENCY_SCRAPER) Scraper currencyScraper
            , @Qualifier(value = Qualifiers.COUNTRIES_SCRAPER) Scraper countriesScraper
            , @Qualifier(value = Qualifiers.LOC_SCRAPER) Scraper locScraper
            , @Qualifier(value = Qualifiers.HOTEL_SEEDER) Seeder hotelSeeder
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
    }


    @Override
    @PostConstruct
    public void initialize() throws IOException {
        //this.currencyScraper.scrape();
        //this.countriesScraper.scrape();
        //this.hotelSeeder.seedData();
        this.locScraper.scrape();
        this.cache.cacheRatesTable(this.rateService.getTop8CurrenciesRates());
        this.cache.cacheCurrencies(this.currencyService.findAll());
        this.rateLogService.logTop8Rates(this.cache.getTop8Rates());
        this.rateLogService.markIfRatesHaveDropped(this.cache.getTop8Rates());
    }
}
