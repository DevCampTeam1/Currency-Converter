package com.devcamp.currencyconverter.scrapers;

import com.devcamp.currencyconverter.entities.Country;
import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.services.api.CountryService;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CountriesScraper {

    private static final String URL = "http://www.exchange-rate.com/currency-list.html";
    private static final String TABLE_TAG = "table";
    private static final String TABLE_BODY_TAG = "tbody";
    private static final String COUNTRY_NAME_TAG = "b";
    private static final String COUNTRY_CODE_TAG = "div";

    private CountryService countryService;
    private CurrencyService currencyService;

    @Autowired
    public CountriesScraper(CountryService countryService, CurrencyService currencyService) {
        this.countryService = countryService;
        this.currencyService = currencyService;
    }

    //@PostConstruct
    public void scrape() throws IOException {

        Document document = Jsoup.connect(URL).timeout(0).get();
        Element table = document.getElementsByTag(TABLE_TAG).get(0);
        Element tableBody = table.getElementsByTag(TABLE_BODY_TAG).first();
        Elements rows = tableBody.children();
        Map<String, Country> countries = new HashMap<>();

        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);

            Elements columns = row.children();
            String countryName = columns.get(0).getElementsByTag(COUNTRY_NAME_TAG).text().split(",\\s+")[0];
            String currencyCode = columns.get(2).getElementsByTag(COUNTRY_CODE_TAG).text();

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
