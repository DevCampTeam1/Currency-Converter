package com.devcamp.currencyconverter.tools.converters.api;

import com.devcamp.currencyconverter.entities.Currency;

import java.math.BigDecimal;

public interface Converter {
    BigDecimal convert(BigDecimal sum, Currency currency);
}
