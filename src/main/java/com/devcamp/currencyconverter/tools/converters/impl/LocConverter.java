package com.devcamp.currencyconverter.tools.converters.impl;

import com.devcamp.currencyconverter.constants.Currencies;
import com.devcamp.currencyconverter.constants.Qualifiers;
import com.devcamp.currencyconverter.model.entities.Currency;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import com.devcamp.currencyconverter.services.api.RateService;
import com.devcamp.currencyconverter.tools.converters.api.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component(value = Qualifiers.LOC_CONVERTER)
public class LocConverter implements Converter {

    private BigDecimal locToUsdRate;

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
        BigDecimal rateToUSD = this.rateService.getRate(currency, usdCurrency).getRate();

        return rateToUSD.multiply(sum)
                .divide(locToUsdRate, RoundingMode.HALF_UP)
                .setScale(Currencies.DECIMAL_SCALE, RoundingMode.HALF_UP);
    }

    public void setLocToUsdRate(BigDecimal locToUsdRate) {
        this.locToUsdRate = locToUsdRate;
    }
}
