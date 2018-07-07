package com.devcamp.currencyconverter.tools.scrapers.impl;

import com.devcamp.currencyconverter.constants.ConsoleMessages;
import com.devcamp.currencyconverter.constants.Qualifiers;
import com.devcamp.currencyconverter.entities.Rate;
import com.devcamp.currencyconverter.tools.io.api.ConsoleIO;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import com.devcamp.currencyconverter.services.api.RateService;
import com.devcamp.currencyconverter.tools.scrapers.api.Scraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.devcamp.currencyconverter.entities.Currency;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Component(value = Qualifiers.CURRENCY_SCRAPER)
public final class CurrencyScraper implements Scraper {

    private static final String URL = "https://www.xe.com/currencytables/?from=";
    private static final String TABLE_ID = "historicalRateTbl";
    private static final String TABLE_BODY_TAG = "tbody";
    private static final int URLS_COUNT = 166;
    private static final int CURRENCY_CODE_INDEX = 0;
    private static final int CURRENCY_RATE_INDEX = 2;
    private static final String INITIAL_CURRENCY = "USD";

    private RateService rateService;
    private CurrencyService currencyService;
    private ConsoleIO consoleIO;

    @Autowired
    public CurrencyScraper(RateService rateService, CurrencyService currencyService, ConsoleIO consoleIO) {
        this.rateService = rateService;
        this.currencyService = currencyService;
        this.consoleIO = consoleIO;
    }

    @Override
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
             this.currencyService.save(new Currency(code));
            // -------------------------- //
        }

        // If database does not exist //
        // -------------------------- //
         Map<String, List<Currency>> allCurrencies = this.currencyService.findAll().stream()
                .collect(Collectors.groupingBy(Currency::getCode));
        // -------------------------- //

        Map<String, Map<String, Rate>> rates = new HashMap<>();
        for (String code : allCurrencyCodes) {
            rates.put(code, new HashMap<>());
        }
        this.rateService.findAll().forEach(r ->
                rates.get(r.getSourceCurrency().getCode()).put(r.getTargetCurrency().getCode(), r)
        );

        int counter = URLS_COUNT;
        for (String currencyCode : allCurrencyCodes) {
            document = Jsoup.connect(URL + currencyCode).timeout(0).get();
            table = document.getElementById(TABLE_ID);
            tableBody = table.getElementsByTag(TABLE_BODY_TAG).first();
            rows = tableBody.children();

            // If database does not exist //
             //-------------------------- //
             Currency source = allCurrencies.get(currencyCode).get(0);
            // -------------------------- //
            Map<String, Rate> currentRates = rates.get(currencyCode);

            for (Element row : rows) {
                Elements cols = row.children();
                String secondCurrencyCode = cols.get(CURRENCY_CODE_INDEX).text();
                BigDecimal rateValue = new BigDecimal(cols.get(CURRENCY_RATE_INDEX).text());

                // If database does not exist //
                // -------------------------- //
                 Currency target = allCurrencies.get(secondCurrencyCode).get(0);
                // -------------------------- //

                Rate rate = currentRates.get(secondCurrencyCode);

                // If database does not exist //
                // -------------------------- //
                 if (rate == null) {
                     rate = new Rate(source, target, rateValue);
                 } else {
                // -------------------------- //
                int comp = rate.getRate().compareTo(rateValue);
                if (comp < 0) {
                    rate.setRateHasDropped(true);
                } else if (comp > 0) {
                    rate.setRateHasDropped(false);
                }
                rate.setRate(rateValue);
                // -------------------------- //
                 }
                // -------------------------- //
                this.rateService.save(rate);
            }
            this.consoleIO.write(counter--);
        }
        this.consoleIO.write(ConsoleMessages.UPDATING_DATABASE);
        rates.forEach((key, value) -> value.values().forEach(r -> this.rateService.save(r)));
        this.consoleIO.write(ConsoleMessages.FINISHED);
    }
}
