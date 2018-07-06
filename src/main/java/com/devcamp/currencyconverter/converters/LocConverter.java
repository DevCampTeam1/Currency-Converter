package com.devcamp.currencyconverter.converters;

import com.devcamp.currencyconverter.constants.Currencies;
import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import com.devcamp.currencyconverter.services.api.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class LocConverter {

    private static final double USD_TO_LOCK_RATE = 0.82;

    private CurrencyService currencyService;
    private RateService rateService;

    @Autowired
    public LocConverter(CurrencyService currencyService, RateService rateService) {
        this.currencyService = currencyService;
        this.rateService = rateService;
    }

    public BigDecimal convert(BigDecimal sum, Currency currency) {
        Currency usdCurrency = this.currencyService.getCurrency(Currencies.DEFAULT_TARGET_CURRENCY);
        Double rateToUSD = this.rateService.getRate(currency, usdCurrency).getRate();

        return BigDecimal.valueOf(rateToUSD).multiply(sum)
                .divide(BigDecimal.valueOf(USD_TO_LOCK_RATE), RoundingMode.HALF_UP);
    }
}
