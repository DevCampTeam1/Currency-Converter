package com.devcamp.currencyconverter.cache;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Rate;
import com.devcamp.currencyconverter.entities.RateLog;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import com.devcamp.currencyconverter.services.api.RateLogService;
import com.devcamp.currencyconverter.services.api.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Cache {
    private RateService rateService;
    private CurrencyService currencyService;
    private RateLogService rateLogService;

    private List<List<Rate>> top8Rates;
    private List<Currency> allCurrencies;

    @Autowired
    public Cache(RateService rateService, CurrencyService currencyService, RateLogService rateLogService) {
        this.rateService = rateService;
        this.currencyService = currencyService;
        this.rateLogService = rateLogService;
    }

    public void initiate() {
        this.top8Rates = this.rateService.getTop8CurrenciesRates().stream()
                .collect(Collectors.groupingBy(Rate::getSourceCurrency))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getKey().getId()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        this.allCurrencies = this.currencyService.findAll();
        if (this.rateLogService.shouldCreateLog(LocalDate.now())) {
            this.logTop8Rates();
        }
        this.checkIfRateHasDropped();
    }

    private void checkIfRateHasDropped() {
        this.top8Rates.stream()
                .flatMap(Collection::stream)
                .forEach(rate -> {
                    LocalDate date = LocalDate.now();
                    RateLog rateLog = this.rateLogService.getRateLog(rate.getSourceCurrency()
                            , rate.getTargetCurrency(), date);
                    int comp = rate.getRate().compareTo(rateLog.getRate());
                    if (comp < 0) {
                        rate.setRateHasDropped(true);
                    } else if (comp > 0){
                        rate.setRateHasDropped(false);
                    }
                });
    }

    public List<List<Rate>> getTop8Rates() {
        return this.top8Rates;
    }

    public List<Currency> getAllCurrencies() {
        return this.allCurrencies;
    }

    private void logTop8Rates() {
        this.top8Rates.stream()
                .flatMap(Collection::stream)
                .forEach(rate -> {

                    LocalDate date = LocalDate.now();
                    RateLog rateLog = new RateLog(rate.getSourceCurrency()
                            , rate.getTargetCurrency()
                            , rate.getRate()
                            , date);
                    this.rateLogService.save(rateLog);
                });

    }
}
