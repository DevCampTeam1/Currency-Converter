package com.devcamp.currencyconverter.cache;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Rate;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import com.devcamp.currencyconverter.services.api.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Cache {
    private RateService rateService;
    private CurrencyService currencyService;

    private List<List<Rate>> top8Rates;
    private List<Currency> allCurrencies;

    @Autowired
    public Cache(RateService rateService, CurrencyService currencyService) {
        this.rateService = rateService;
        this.currencyService = currencyService;
    }

    @PostConstruct
    public void initiate() {
        this.top8Rates = this.rateService.getTop8CurrenciesRates().stream()
                .collect(Collectors.groupingBy(Rate::getSourceCurrency))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getKey().getId()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        this.allCurrencies = this.currencyService.findAll();

    }

    public List<List<Rate>> getTop8Rates() {
        return this.top8Rates;
    }

    public List<Currency> getAllCurrencies() {
        return this.allCurrencies;
    }
}
