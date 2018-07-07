package com.devcamp.currencyconverter.tools.scrapers.impl;

import com.devcamp.currencyconverter.constants.Qualifiers;
import com.devcamp.currencyconverter.model.entities.Country;
import com.devcamp.currencyconverter.model.entities.Currency;
import com.devcamp.currencyconverter.services.api.CountryService;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import com.devcamp.currencyconverter.tools.scrapers.api.Scraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(value = Qualifiers.COUNTRIES_SCRAPER)
public class CountriesScraper implements Scraper {

    private static final String URL = "http://www.exchange-rate.com/currency-list.html";
    private static final String TABLE_TAG = "table";
    private static final String TABLE_BODY_TAG = "tbody";
    private static final String COUNTRY_NAME_TAG = "b";
    private static final String COUNTRY_CODE_TAG = "div";

    private static final String SPLIT_DELIMITER = ",\\s+";
    private static final int COUNTRY_NAME_INDEX = 0;
    private static final int COUNTRY_CODE_INDEX = 2;


    private CountryService countryService;
    private CurrencyService currencyService;

    @Autowired
    public CountriesScraper(CountryService countryService, CurrencyService currencyService) {
        this.countryService = countryService;
        this.currencyService = currencyService;
    }

    @Override
    public void scrape() throws IOException {

        Document document = Jsoup.connect(URL).timeout(0).get();
        Element table = document.getElementsByTag(TABLE_TAG).get(0);
        Element tableBody = table.getElementsByTag(TABLE_BODY_TAG).first();
        Elements rows = tableBody.children();
        Map<String, Country> countries = new HashMap<>();

        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);

            Elements columns = row.children();
            String countryName = columns.get(COUNTRY_NAME_INDEX).getElementsByTag(COUNTRY_NAME_TAG).text()
                    .split(SPLIT_DELIMITER)[0];
            String currencyCode = columns.get(COUNTRY_CODE_INDEX).getElementsByTag(COUNTRY_CODE_TAG).text();

            Currency currency = this.currencyService.getCurrency(currencyCode);
            if (currency != null) {
                Country country = new Country(countryName, currency);
                countries.putIfAbsent(countryName, country);
            }
        }
        for (Country country : countries.values()) {
            this.countryService.save(country);
        }
    }
}
