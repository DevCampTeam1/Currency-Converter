package com.devcamp.currencyconverter.services.impl;

import com.devcamp.currencyconverter.constants.Qualifiers;
import com.devcamp.currencyconverter.model.entities.Currency;
import com.devcamp.currencyconverter.model.entities.Rate;
import com.devcamp.currencyconverter.model.views.RateView;
import com.devcamp.currencyconverter.repositories.RateRepository;
import com.devcamp.currencyconverter.services.api.RateService;
import com.devcamp.currencyconverter.tools.mapper.api.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private Mapper mapper;

    @Autowired
    public RateServiceImpl(RateRepository currencyRepository
            , @Qualifier(Qualifiers.MODEL_MAPPER) Mapper mapper) {
        this.rateRepository = currencyRepository;
        this.mapper = mapper;
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
    public List<List<RateView>> getTop8CurrenciesRates() {
        return this.rateRepository.getTop8CurrenciesRates().stream()
                .map(r -> this.mapper.convert(r, RateView.class))
                .collect(Collectors.groupingBy(RateView::getSourceCurrency))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getKey().getId()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
