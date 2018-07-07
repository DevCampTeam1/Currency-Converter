package com.devcamp.currencyconverter.services.api;

import com.devcamp.currencyconverter.model.entities.Currency;
import com.devcamp.currencyconverter.model.entities.RateLog;
import com.devcamp.currencyconverter.model.views.RateView;

import java.time.LocalDate;
import java.util.List;

public interface RateLogService {
    void save(RateLog rateLog);

    RateLog getRateLog(Currency source, Currency target, LocalDate date);

    boolean shouldCreateLog(LocalDate date);

    void logTop8Rates(List<List<RateView>> top8Rates);

    void markIfRatesHaveDropped(List<List<RateView>> top8Rates);
}
