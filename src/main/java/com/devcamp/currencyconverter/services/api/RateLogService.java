package com.devcamp.currencyconverter.services.api;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.RateLog;

import java.time.LocalDate;

public interface RateLogService {
    void save(RateLog rateLog);

    RateLog getRateLog(Currency source, Currency target, LocalDate date);

    boolean shouldCreateLog(LocalDate date);
}
