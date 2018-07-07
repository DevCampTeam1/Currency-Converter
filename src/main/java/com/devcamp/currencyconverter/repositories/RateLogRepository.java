package com.devcamp.currencyconverter.repositories;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.RateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RateLogRepository extends JpaRepository<RateLog, Long> {
    RateLog findFirstByDate(LocalDate date);

    RateLog findBySourceCurrencyAndTargetCurrencyAndDate(Currency source, Currency target, LocalDate date);
}
