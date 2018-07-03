package com.devcamp.currencyconverter.repositories;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    Rate findBySourceCurrencyAndTargetCurrency(Currency source, Currency target);

    List<Rate> findAllBySourceCurrency(Currency source);
}
