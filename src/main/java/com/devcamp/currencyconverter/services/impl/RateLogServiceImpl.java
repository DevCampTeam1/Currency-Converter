package com.devcamp.currencyconverter.services.impl;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.RateLog;
import com.devcamp.currencyconverter.repositories.RateLogRepository;
import com.devcamp.currencyconverter.services.api.RateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class RateLogServiceImpl implements RateLogService {

    private RateLogRepository rateLogRepository;

    @Autowired
    public RateLogServiceImpl(RateLogRepository rateLogRepository) {
        this.rateLogRepository = rateLogRepository;
    }

    @Override
    public void save(RateLog rateLog) {
        this.rateLogRepository.saveAndFlush(rateLog);
    }

    @Override
    public RateLog getRateLog(Currency source, Currency target, LocalDate date) {
        return this.rateLogRepository.findBySourceCurrencyAndTargetCurrencyAndDate(source, target, date);
    }

    @Override
    public boolean shouldCreateLog(LocalDate date) {
       return this.rateLogRepository.findFirstByDate(date) == null;
    }
}
