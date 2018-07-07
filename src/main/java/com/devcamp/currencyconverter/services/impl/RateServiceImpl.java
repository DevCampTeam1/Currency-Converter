package com.devcamp.currencyconverter.services.impl;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Rate;
import com.devcamp.currencyconverter.repositories.RateRepository;
import com.devcamp.currencyconverter.services.api.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class RateServiceImpl implements RateService {

    private RateRepository rateRepository;

    @Autowired
    public RateServiceImpl(RateRepository currencyRepository) {
        this.rateRepository = currencyRepository;
    }

    @Override
    public List<Rate> findAll() {
        return this.rateRepository.findAll();
    }

    @Override
    public Rate getRate(Currency source, Currency target) {
        return this.rateRepository.findBySourceCurrencyAndTargetCurrency(source, target);
    }

    @Override
    public void save(Rate currency) {
        this.rateRepository.saveAndFlush(currency);
    }

    @Override
    public List<Rate> getAllWithSourceCurrency(Currency currency) {
        return this.rateRepository.findAllBySourceCurrency(currency);
    }

    @Override
    public List<List<Rate>> getTop8CurrenciesRates() {
        return this.rateRepository.getTop8CurrenciesRates().stream()
                .collect(Collectors.groupingBy(Rate::getSourceCurrency))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getKey().getId()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
