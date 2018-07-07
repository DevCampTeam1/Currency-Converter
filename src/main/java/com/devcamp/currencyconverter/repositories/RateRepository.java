package com.devcamp.currencyconverter.repositories;

import com.devcamp.currencyconverter.model.entities.Currency;
import com.devcamp.currencyconverter.model.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    Rate findBySourceCurrencyAndTargetCurrency(Currency source, Currency target);

    List<Rate> findAllBySourceCurrency(Currency currency);

    @Query(value = "SELECT r.id, r.source_currency_id, r.target_currency_id, TRUNCATE(r.rate, 2) AS rate\n" +
            "FROM currency_converter.rates AS r\n" +
            "WHERE r.source_currency_id IN(1,2,3,4,5,6,7,64)\n" +
            "AND r.target_currency_id IN(1,2,3,4,5,6,7,64)"
            , nativeQuery = true)
    List<Rate> getTop8CurrenciesRates();
}
