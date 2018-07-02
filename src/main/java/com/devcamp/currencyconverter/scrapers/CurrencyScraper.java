package com.devcamp.currencyconverter.scrapers;

import com.devcamp.currencyconverter.CurrencyConverterApplication;
import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public final class CurrencyScraper {

    private static final String URL = "https://www.xe.com/currencytables/?from=USD";
    private static final String TABLE_ID = "historicalRateTbl";
    private static final String TABLE_BODY_TAG = "tbody";
    private static final int CURRENCY_CODE_INDEX = 0;
    private static final int CURRENCY_NAME_INDEX = 1;
    private static final int CURRENCY_RATE_INDEX = 3;
    private CurrencyService currencyService;

    @Autowired
    public CurrencyScraper(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostConstruct
    public void scrape() throws IOException {
        List<Currency> currencies = this.currencyService.findAll();

        Document document = Jsoup.connect(URL).get();

        Element table = document.getElementById(TABLE_ID);
        Element tableBody = table.getElementsByTag(TABLE_BODY_TAG).first();
        Elements rows = tableBody.children();

        for (Element row : rows) {
            Elements cols = row.children();
            String currencyCode = cols.get(CURRENCY_CODE_INDEX).text();
            String currencyName = cols.get(CURRENCY_NAME_INDEX).text();
            BigDecimal rateToUSD = new BigDecimal(cols.get(CURRENCY_RATE_INDEX).text());

            Optional<Currency> currencyOpt = currencies.stream()
                    .filter(c -> c.getCurrencyCode().equals(currencyCode))
                    .findFirst();
            Currency currency = null;

            if (currencyOpt.isPresent()) {
                currency = currencyOpt.get();
                currency.setRate(rateToUSD);
            } else {
                currency = new Currency(currencyCode, currencyName, rateToUSD);
            }

            currency.setRate(rateToUSD);
            this.currencyService.save(currency);
        }
    }
}
