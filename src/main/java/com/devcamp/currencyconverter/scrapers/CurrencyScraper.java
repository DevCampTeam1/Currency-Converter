package com.devcamp.currencyconverter.scrapers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public final class CurrencyScraper {

    private static final String URL = "https://www.xe.com/currencytables/?from=USD";
    private static final String TABLE_ID = "historicalRateTbl";
    private static final String TABLE_BODY_TAG = "tbody";
    private static final int CURRENCY_CODE_INDEX = 0;
    private static final int CURRENCY_NAME_INDEX = 1;
    private static final int CURRENCY_RATE_INDEX = 3;

    @PostConstruct
    public static void scrape() {
        try {
            Document document = Jsoup.connect(URL).get();

            Element table = document.getElementById(TABLE_ID);
            Element tableBody = table.getElementsByTag(TABLE_BODY_TAG).first();
            Elements rows = tableBody.children();

            for (Element row : rows) {
                Elements cols = row.children();
                String currencyCode = cols.get(CURRENCY_CODE_INDEX).text();
                String currencyName = cols.get(CURRENCY_NAME_INDEX).text();
                String rateToUSD = cols.get(CURRENCY_RATE_INDEX).text();
                System.out.printf("%s %s %s%n", currencyCode, currencyName, rateToUSD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
