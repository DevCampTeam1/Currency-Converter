package com.devcamp.currencyconverter.repositories;

import com.devcamp.currencyconverter.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByCurrencyCode(String code);
}
