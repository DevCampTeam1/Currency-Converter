package com.devcamp.currencyconverter.services.api;

import com.devcamp.currencyconverter.model.entities.Currency;
import com.devcamp.currencyconverter.model.entities.Rate;
import com.devcamp.currencyconverter.model.views.RateView;

import java.util.List;

public interface RateService {
    List<Rate> findAll();

    Rate getRate(Currency source, Currency target);

    void save(Rate currency);

    List<Rate> getAllWithSourceCurrency(Currency currency);

    List<List<RateView>> getTop8CurrenciesRates();
}
