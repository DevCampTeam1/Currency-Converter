package com.devcamp.currencyconverter.controllers;

import com.devcamp.currencyconverter.cache.Cache;
import com.devcamp.currencyconverter.constants.Currencies;
import com.devcamp.currencyconverter.constants.ErrorMessages;
import com.devcamp.currencyconverter.constants.Placeholders;
import com.devcamp.currencyconverter.constants.Templates;
import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Rate;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import com.devcamp.currencyconverter.services.api.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
public class IndexController {

    private RateService rateService;
    private CurrencyService currencyService;
    private Cache cache;

    @Autowired
    public IndexController(RateService rateService, CurrencyService currencyService, Cache cache) {
        this.rateService = rateService;
        this.currencyService = currencyService;
        this.cache = cache;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Currency> currencies = this.cache.getAllCurrencies();
        Currency source = this.currencyService.getCurrency(Currencies.DEFAULT_SOURCE_CURRENCY);
        Currency target = this.currencyService.getCurrency(Currencies.DEFAULT_TARGET_CURRENCY);
        BigDecimal rate = BigDecimal.valueOf(this.rateService.getRate(source, target).getRate())
                .setScale(Currencies.DECIMAL_SCALE, RoundingMode.HALF_UP);
        List<List<Rate>> top10Rates = this.cache.getTop10Rates();

        addAttributes(model, currencies, rate, top10Rates);
        return Templates.BASE;
    }

    @PostMapping("/")
    public String indexPost(Model model
            , @RequestParam String sourceCurrency
            , @RequestParam String targetCurrency
            , @RequestParam String sum) {

        List<Currency> currencies = this.cache.getAllCurrencies();
        Currency source = this.currencyService.getCurrency(sourceCurrency);
        Currency target = this.currencyService.getCurrency(targetCurrency);
        BigDecimal result = validateInput(model, sum, source, target);

        List<List<Rate>> top10Rates = this.cache.getTop10Rates();
        addAttributes(model, currencies, result, top10Rates);
        return Templates.BASE;
    }

    private BigDecimal validateInput(Model model, @RequestParam String sum, Currency source, Currency target) {
        BigDecimal result = BigDecimal.ZERO;
        if (source == null) {
            model.addAttribute(Placeholders.INVALID_SOURCE_CURRENCY_MESSAGE, ErrorMessages.INVALID_CURRENCY);
        }
        if (target == null) {
            model.addAttribute(Placeholders.INVALID_TARGET_CURRENCY_MESSAGE, ErrorMessages.INVALID_CURRENCY);
        }
        if (source != null && target != null) {
            if (sumIsNegative(sum)) {
                model.addAttribute(Placeholders.INVALID_SUM_MESSAGE, ErrorMessages.INVALID_SUM);
            } else {
                Rate rate = this.rateService.getRate(source, target);
                result = new BigDecimal(rate.getRate()).multiply(new BigDecimal(sum))
                        .setScale(Currencies.DECIMAL_SCALE, RoundingMode.HALF_UP);
            }
        }
        return result;
    }

    private boolean sumIsNegative(@RequestParam String sum) {
        return new BigDecimal(sum).compareTo(BigDecimal.ZERO) < 0;
    }

    private void addAttributes(Model model, List<Currency> currencies
            , BigDecimal rate, List<List<Rate>> top10Rates) {
        model.addAttribute(Placeholders.TOP_10_RATES, top10Rates);
        model.addAttribute(Placeholders.SOURCE_CURRENCY, Currencies.DEFAULT_SOURCE_CURRENCY);
        model.addAttribute(Placeholders.TARGET_CURRENCY, Currencies.DEFAULT_TARGET_CURRENCY);
        model.addAttribute(Placeholders.CURRENCIES, currencies);
        model.addAttribute(Placeholders.INPUT_SUM, Currencies.DEFAULT_SUM);
        model.addAttribute(Placeholders.RESULT, rate);
        model.addAttribute(Placeholders.VIEW, Templates.INDEX);
    }
}
