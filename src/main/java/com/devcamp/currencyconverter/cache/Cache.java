package com.devcamp.currencyconverter.cache;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Rate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public final class Cache {
    private List<List<Rate>> top8Rates;
    private List<Currency> allCurrencies;

    public void cacheRatesTable(List<List<Rate>> top8Rates){
        this.top8Rates = top8Rates;
    }

    public void cacheCurrencies(List<Currency> allCurrencies){
        this.allCurrencies = allCurrencies;
    }

    public List<List<Rate>> getTop8Rates() {
        return this.top8Rates;
    }

    public List<Currency> getAllCurrencies() {
        return this.allCurrencies;
    }
}
