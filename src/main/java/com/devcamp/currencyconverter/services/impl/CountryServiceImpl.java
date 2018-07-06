package com.devcamp.currencyconverter.services.impl;

import com.devcamp.currencyconverter.entities.Country;
import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.repositories.CountryRepository;
import com.devcamp.currencyconverter.services.api.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> findAllByCurrency(Currency currency) {
        return this.countryRepository.findAllByCurrency(currency);
    }

    @Override
    public void save(Country country) {
        this.countryRepository.saveAndFlush(country);
    }
}
