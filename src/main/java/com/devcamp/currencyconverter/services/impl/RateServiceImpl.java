package com.devcamp.currencyconverter.services.impl;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Rate;
import com.devcamp.currencyconverter.repositories.RateRepository;
import com.devcamp.currencyconverter.services.api.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RateServiceImpl implements RateService {

    private RateRepository currencyRepository;

    @Autowired
    public RateServiceImpl(RateRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Rate> findAll() {
        return this.currencyRepository.findAll();
    }

    @Override
    public Rate getRate(Currency source, Currency target) {
        return this.currencyRepository.findBySourceCurrencyAndTargetCurrency(source, target);
    }

    @Override
    public void save(Rate currency) {
        this.currencyRepository.saveAndFlush(currency);
    }

    @Override
    public List<Rate> getAllWithSourceCurrency(Currency currency) {
        return this.currencyRepository.findAllBySourceCurrency(currency);
    }
}
