package com.devcamp.currencyconverter.tools.converters.impl;

import com.devcamp.currencyconverter.constants.Currencies;
import com.devcamp.currencyconverter.constants.Qualifiers;
import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import com.devcamp.currencyconverter.services.api.RateService;
import com.devcamp.currencyconverter.tools.converters.api.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component(value = Qualifiers.LOC_CONVERTER)
public class LocConverter implements Converter {

    private static final double USD_TO_LOCK_RATE = 0.82;

    private CurrencyService currencyService;
    private RateService rateService;

    @Autowired
    public LocConverter(CurrencyService currencyService, RateService rateService) {
        this.currencyService = currencyService;
        this.rateService = rateService;
    }

    @Override
    public BigDecimal convert(BigDecimal sum, Currency currency) {
        if (sum.compareTo(BigDecimal.ZERO) == 0) {
            return sum;
        }
        Currency usdCurrency = this.currencyService.getCurrency(Currencies.DEFAULT_TARGET_CURRENCY);
        Double rateToUSD = this.rateService.getRate(currency, usdCurrency).getRate();

        return BigDecimal.valueOf(rateToUSD).multiply(sum)
                .divide(BigDecimal.valueOf(USD_TO_LOCK_RATE), RoundingMode.HALF_UP)
                .setScale(Currencies.DECIMAL_SCALE, RoundingMode.HALF_UP);
    }
}
