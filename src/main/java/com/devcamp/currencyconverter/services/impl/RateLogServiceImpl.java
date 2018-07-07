package com.devcamp.currencyconverter.services.impl;

import com.devcamp.currencyconverter.model.entities.Currency;
import com.devcamp.currencyconverter.model.entities.Rate;
import com.devcamp.currencyconverter.model.entities.RateLog;
import com.devcamp.currencyconverter.model.views.RateView;
import com.devcamp.currencyconverter.repositories.RateLogRepository;
import com.devcamp.currencyconverter.services.api.RateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

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

    @Override
    public void logTop8Rates(List<List<RateView>> top8Rates) {
        if (this.shouldCreateLog(LocalDate.now())) {
            top8Rates.stream()
                    .flatMap(Collection::stream)
                    .forEach(rate -> {
                        LocalDate date = LocalDate.now();
                        RateLog rateLog = new RateLog(rate.getSourceCurrency()
                                , rate.getTargetCurrency()
                                , rate.getRate()
                                , date);
                        this.rateLogRepository.save(rateLog);
                    });
        }
    }

    @Override
    public void markIfRatesHaveDropped(List<List<RateView>> top8Rates) {
        top8Rates.stream()
                .flatMap(Collection::stream)
                .forEach(rate -> {
                    LocalDate date = LocalDate.now().minusDays(0);
                    RateLog rateLog = this.rateLogRepository.findBySourceCurrencyAndTargetCurrencyAndDate(
                            rate.getSourceCurrency(), rate.getTargetCurrency(), date);
                    if (rateLog != null) {
                        int comp = rate.getRate().compareTo(rateLog.getRate());
                        if (comp > 0) {
                            rate.setRateHasDropped(true);
                        } else if (comp < 0) {
                            rate.setRateHasDropped(false);
                        }
                    }
                });
    }
}
