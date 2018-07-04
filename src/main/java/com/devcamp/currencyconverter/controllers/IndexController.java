package com.devcamp.currencyconverter.controllers;

import com.devcamp.currencyconverter.cache.Cache;
import com.devcamp.currencyconverter.constants.ErrorMessages;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Currency> currencies = this.currencyService.findAll();
        Currency source = this.currencyService.getCurrency("BGN");
        Currency target = this.currencyService.getCurrency("USD");
        Rate rate = this.rateService.getRate(source, target);

        List<List<Rate>> top10Rates = this.cache.getTop10Rates();

        model.addAttribute("rates", top10Rates);
        model.addAttribute("sourceCurrency", "BGN");
        model.addAttribute("targetCurrency", "USD");
        model.addAttribute("currencies", currencies);
        model.addAttribute("sum", 1);
        model.addAttribute("result", rate.getRate());
        model.addAttribute("view", "home/index");
        return "base-layout";
    }

    @PostMapping("/")
    public String indexPost(Model model
            , @RequestParam String sourceCurrency
            , @RequestParam String targetCurrency
            , @RequestParam String sum) {

        List<Currency> currencies = this.currencyService.findAll();
        Currency source = this.currencyService.getCurrency(sourceCurrency);
        Currency target = this.currencyService.getCurrency(targetCurrency);
        BigDecimal result;
        if (source == null || target == null) {
            model.addAttribute("message", ErrorMessages.INVALID_CURRENCY);
            result = BigDecimal.ZERO;
        } else {
            Rate rate = this.rateService.getRate(source, target);
            result = new BigDecimal(rate.getRate()).multiply(new BigDecimal(sum));
        }
        List<List<Rate>> top10Rates = this.cache.getTop10Rates();
        model.addAttribute("rates", top10Rates);
        model.addAttribute("sourceCurrency", sourceCurrency);
        model.addAttribute("targetCurrency", targetCurrency);
        model.addAttribute("currencies", currencies);
        model.addAttribute("sum", sum);
        model.addAttribute("result", result);
        model.addAttribute("view", "home/index");
        return "base-layout";
    }
}
