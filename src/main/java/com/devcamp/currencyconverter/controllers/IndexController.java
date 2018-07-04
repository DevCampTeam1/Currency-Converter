package com.devcamp.currencyconverter.controllers;

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

    @Autowired
    public IndexController(RateService rateService, CurrencyService currencyService) {
        this.rateService = rateService;
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Currency> currencies = this.currencyService.findAll();
        Currency source = this.currencyService.getCurrency("BGN");
        Currency target = this.currencyService.getCurrency("USD");
        Rate rate = this.rateService.getRate(source, target);

        //

        List<List<Rate>> top10Rates = this.rateService.getTop10CurrenciesRates().stream()
                .collect(Collectors.groupingBy(Rate::getSourceCurrency))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getKey().getId()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        //top10Rates.forEach(l -> l.sort(Comparator.comparing(Rate::getId)));

        model.addAttribute("rates", top10Rates);
        //

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
        Rate rate = this.rateService.getRate(source, target);
        BigDecimal result = new BigDecimal(rate.getRate()).multiply(new BigDecimal(sum));

        model.addAttribute("sourceCurrency", sourceCurrency);
        model.addAttribute("targetCurrency", targetCurrency);
        model.addAttribute("currencies", currencies);
        model.addAttribute("sum", sum);
        model.addAttribute("result", result);
        model.addAttribute("view", "home/index");
        return "base-layout";
    }
}
