package com.devcamp.currencyconverter.cache;

import com.devcamp.currencyconverter.model.views.CurrencyView;
import com.devcamp.currencyconverter.model.views.RateView;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public final class Cache {
    private List<List<RateView>> top8Rates;
    private List<CurrencyView> allCurrencies;

    public void cacheRatesTable(List<List<RateView>> top8Rates) {
        this.top8Rates = top8Rates;
    }

    public void cacheCurrencies(List<CurrencyView> allCurrencies) {
        this.allCurrencies = allCurrencies;
    }

    public List<List<RateView>> getTop8Rates() {
        return this.top8Rates;
    }

    public List<CurrencyView> getAllCurrencies() {
        return this.allCurrencies;
    }
}
