package com.devcamp.currencyconverter.repositories;

import com.devcamp.currencyconverter.entities.Country;
import com.devcamp.currencyconverter.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findAllByCurrency(Currency currency);

    Country findByName(String name);
}
