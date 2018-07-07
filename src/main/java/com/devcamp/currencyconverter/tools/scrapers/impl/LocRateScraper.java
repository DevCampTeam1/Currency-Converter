package com.devcamp.currencyconverter.tools.scrapers.impl;

import com.devcamp.currencyconverter.constants.Qualifiers;
import com.devcamp.currencyconverter.tools.converters.impl.LocConverter;
import com.devcamp.currencyconverter.tools.scrapers.api.Scraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

@Component(value = Qualifiers.LOC_SCRAPER)
public class LocRateScraper implements Scraper {

    private static final String URL = "https://digitalcoinprice.com/coins/lockchain";
    private static final String LOC_PRICE_ID = "quote_price";
    private static final int LOC_PRICE_START_INDEX = 1;

    private LocConverter locConverter;

    @Autowired
    public LocRateScraper(LocConverter locConverter) {
        this.locConverter = locConverter;
    }

    @Override
    public void scrape() throws IOException {
        Document document = Jsoup.connect(URL).timeout(0).get();
        Element priceElement = document.getElementById(LOC_PRICE_ID);
        BigDecimal price = new BigDecimal(priceElement.text().substring(LOC_PRICE_START_INDEX));
        this.locConverter.setLocToUsdRate(price);
    }
}
