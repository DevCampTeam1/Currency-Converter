package com.devcamp.currencyconverter.services.api;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Rate;
import com.devcamp.currencyconverter.entities.RateLog;

import java.time.LocalDate;
import java.util.List;

public interface RateLogService {
    void save(RateLog rateLog);

    RateLog getRateLog(Currency source, Currency target, LocalDate date);

    boolean shouldCreateLog(LocalDate date);

    void logTop8Rates(List<List<Rate>> top8Rates);

    void markIfRatesHaveDropped(List<List<Rate>> top8Rates);
}
