package com.devcamp.currencyconverter.services.api;

import com.devcamp.currencyconverter.entities.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> findAll();

    Currency getCurrency(String code);

    void save(Currency currency);
}
