package com.devcamp.currencyconverter.controllers;

import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.services.api.CurrencyService;
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

    private CurrencyService currencyService;

    @Autowired
    public IndexController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Currency> currencies = this.currencyService.findAll();
        model.addAttribute("entryCurrency", "USD");
        model.addAttribute("outputCurrency", "BGN");
        model.addAttribute("currencies", currencies);
        model.addAttribute("result", 0);
        model.addAttribute("view", "home/index");
        return "base-layout";
    }

    @PostMapping("/")
    public String indexPost(Model model
            , @RequestParam String entryCurrency
            , @RequestParam String outputCurrency
            , @RequestParam String sum) {
        List<Currency> currencies = this.currencyService.findAll();

        Currency sourceCurrency = this.currencyService.findByCurrencyCode(entryCurrency);
        Currency ouputCurrency = this.currencyService.findByCurrencyCode(outputCurrency);

        BigDecimal firstCurrencyRateToUSD = sourceCurrency.getRate();
        BigDecimal secondCurrencyRateToUSD = ouputCurrency.getRate();

        BigDecimal resultUSD = firstCurrencyRateToUSD.multiply(new BigDecimal(sum));
        BigDecimal result = resultUSD.divide(secondCurrencyRateToUSD, RoundingMode.DOWN)
                .setScale(7, RoundingMode.UNNECESSARY);

        model.addAttribute("entryCurrency", "USD");
        model.addAttribute("outputCurrency", "BGN");
        model.addAttribute("sum", sum);
        model.addAttribute("currencies", currencies);
        model.addAttribute("result", result);
        model.addAttribute("view", "home/index");
        return "base-layout";
    }
}
