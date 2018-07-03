package com.devcamp.currencyconverter.scrapers;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Rate;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import com.devcamp.currencyconverter.services.api.RateService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class CurrencyScraper {

    private static final String URL = "https://www.xe.com/currencytables/?from=";
    private static final String TABLE_ID = "historicalRateTbl";
    private static final String TABLE_BODY_TAG = "tbody";
    private static final int CURRENCY_CODE_INDEX = 0;
    private static final int CURRENCY_RATE_INDEX = 2;
    private static final String INITIAL_CURRENCY = "USD";

    private RateService rateService;
    private CurrencyService currencyService;

    @Autowired
    public CurrencyScraper(RateService rateService, CurrencyService currencyService) {
        this.rateService = rateService;
        this.currencyService = currencyService;
    }

    //@PostConstruct
    public void scrape() throws IOException {
        Document document = Jsoup.connect(URL + INITIAL_CURRENCY).timeout(0).get();
        Element table = document.getElementById(TABLE_ID);
        Element tableBody = table.getElementsByTag(TABLE_BODY_TAG).first();
        Elements rows = tableBody.children();

        List<String> allCurrencyCodes = new ArrayList<>();

        for (Element row : rows) {
            Elements cols = row.children();
            String code = cols.get(CURRENCY_CODE_INDEX).text();
            allCurrencyCodes.add(code);
            // If database does not exist //
            // -------------------------- //
            // this.currencyService.save(new Currency(code));
            // -------------------------- //
        }

        // If database does not exist //
        // -------------------------- //
        // Map<String, List<Currency>> allCurrencies = this.currencyService.findAll().stream()
        //        .collect(Collectors.groupingBy(Currency::getCode));
        // -------------------------- //

        Map<String, Map<String, Rate>> rates = new HashMap<>();
        for (String code : allCurrencyCodes) {
            rates.put(code, new HashMap<>());
        }
        this.rateService.findAll().forEach(r ->
                rates.get(r.getSourceCurrency().getCode()).put(r.getTargetCurrency().getCode(), r)
        );

        int counter = 166;
        for (String currencyCode : allCurrencyCodes) {
            document = Jsoup.connect(URL + currencyCode).timeout(0).get();
            table = document.getElementById(TABLE_ID);
            tableBody = table.getElementsByTag(TABLE_BODY_TAG).first();
            rows = tableBody.children();

            // If database does not exist //
            // -------------------------- //
            // Currency source = allCurrencies.get(currencyCode).get(0);
            // -------------------------- //
            Map<String, Rate> currentRates = rates.get(currencyCode);

            for (Element row : rows) {
                Elements cols = row.children();
                String secondCurrencyCode = cols.get(CURRENCY_CODE_INDEX).text();
                Double rateValue = Double.valueOf(cols.get(CURRENCY_RATE_INDEX).text());

                // If database does not exist //
                // -------------------------- //
                // Currency target = allCurrencies.get(secondCurrencyCode).get(0);
                // -------------------------- //

                Rate rate = currentRates.get(secondCurrencyCode);

                // If database does not exist //
                // -------------------------- //
                // if (rate == null) {
                //     rate = new Rate(source, target, rateValue);
                // } else {
                // -------------------------- //
                rate.setRate(rateValue);
                // -------------------------- //
                // }
                // -------------------------- //
                this.rateService.save(rate);
            }
            System.out.println(counter--);
        }
    }
}
