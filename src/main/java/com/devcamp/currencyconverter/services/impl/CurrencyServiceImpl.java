package com.devcamp.currencyconverter.services.impl;

import com.devcamp.currencyconverter.model.entities.Currency;
import com.devcamp.currencyconverter.repositories.CurrencyRepository;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> findAll() {
        return this.currencyRepository.findAll();
    }

    @Override
    public Currency getCurrency(String code) {
        return this.currencyRepository.findByCode(code);
    }

    @Override
    public void save(Currency currency) {
        this.currencyRepository.saveAndFlush(currency);
    }
}
