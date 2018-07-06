package com.devcamp.currencyconverter.services.api;

import com.devcamp.currencyconverter.entities.Country;
import com.devcamp.currencyconverter.entities.Currency;

import java.util.List;

public interface CountryService {
    List<Country> findAllByCurrency(Currency currency);

    void save(Country country);
}
