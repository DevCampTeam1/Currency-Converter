package com.devcamp.currencyconverter.services.api;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Rate;

import java.util.List;

public interface RateService {
    List<Rate> findAll();

    Rate getRate(Currency source, Currency target);

    void save(Rate currency);

    List<Rate> getAllWithSourceCurrency(Currency currency);

    List<Rate> getTop8CurrenciesRates();
}
