package com.devcamp.currencyconverter.services.api;

import com.devcamp.currencyconverter.model.entities.Country;
import com.devcamp.currencyconverter.model.entities.Currency;

import java.util.List;

public interface CountryService {
    List<Country> findAllByCurrency(Currency currency);

    void save(Country country);

    Country findByName(String name);
}
