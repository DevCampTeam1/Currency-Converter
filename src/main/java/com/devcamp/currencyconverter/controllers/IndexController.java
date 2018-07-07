package com.devcamp.currencyconverter.controllers;

import com.devcamp.currencyconverter.cache.Cache;
import com.devcamp.currencyconverter.constants.*;
import com.devcamp.currencyconverter.tools.converters.api.Converter;
import com.devcamp.currencyconverter.entities.Country;
import com.devcamp.currencyconverter.entities.Currency;
import com.devcamp.currencyconverter.entities.Hotel;
import com.devcamp.currencyconverter.entities.Rate;
import com.devcamp.currencyconverter.services.api.CountryService;
import com.devcamp.currencyconverter.services.api.CurrencyService;
import com.devcamp.currencyconverter.services.api.HotelService;
import com.devcamp.currencyconverter.services.api.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private HotelService hotelService;
    private CountryService countryService;
    private Converter locConverter;
    private Cache cache;

    @Autowired
    public IndexController(RateService rateService, CurrencyService currencyService, HotelService hotelService
            , @Qualifier(value = Qualifiers.LOC_CONVERTER) Converter locConverter
            , Cache cache, CountryService countryService) {
        this.rateService = rateService;
        this.currencyService = currencyService;
        this.hotelService = hotelService;
        this.countryService = countryService;
        this.locConverter = locConverter;
        this.cache = cache;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Currency> currencies = this.cache.getAllCurrencies();
        Currency source = this.currencyService.getCurrency(Currencies.DEFAULT_SOURCE_CURRENCY);
        Currency target = this.currencyService.getCurrency(Currencies.DEFAULT_TARGET_CURRENCY);

        BigDecimal rate = this.rateService.getRate(source, target).getRate()
                .setScale(Currencies.DECIMAL_SCALE, RoundingMode.HALF_UP);

        List<List<Rate>> top8Rates = this.cache.getTop8Rates();
        BigDecimal resultInLoc = this.locConverter.convert(rate, target)
                .setScale(Currencies.DECIMAL_SCALE, RoundingMode.HALF_UP);

        this.addAttributes(model, currencies, Currencies.DEFAULT_SOURCE_CURRENCY, Currencies.DEFAULT_TARGET_CURRENCY
                , Currencies.DEFAULT_SUM, rate, resultInLoc, top8Rates, null);
        return Templates.BASE;
    }

    @PostMapping("/")
    public String indexPost(Model model
            , @RequestParam String sourceCurrency
            , @RequestParam String targetCurrency
            , @RequestParam String sum) {

        List<Currency> currencies = this.cache.getAllCurrencies();
        List<List<Rate>> top8Rates = this.cache.getTop8Rates();
        Currency source = this.currencyService.getCurrency(sourceCurrency);
        Currency target = this.currencyService.getCurrency(targetCurrency);

        if (source == null || target == null || !sumIsValid(sum)) {
            if (source == null) {
                model.addAttribute(Placeholders.INVALID_SOURCE_CURRENCY_MESSAGE, ErrorMessages.INVALID_CURRENCY);
            }
            if (target == null) {
                model.addAttribute(Placeholders.INVALID_TARGET_CURRENCY_MESSAGE, ErrorMessages.INVALID_CURRENCY);
            }
            if (!sumIsValid(sum)) {
                model.addAttribute(Placeholders.INVALID_SUM_MESSAGE, ErrorMessages.INVALID_SUM);
            }
            this.addAttributes(model, currencies, Currencies.DEFAULT_SOURCE_CURRENCY
                    , Currencies.DEFAULT_TARGET_CURRENCY, Currencies.DEFAULT_SUM, BigDecimal.ZERO
                    , BigDecimal.ZERO, top8Rates, null);
            return Templates.BASE;
        }

        Rate rate = this.rateService.getRate(source, target);
        BigDecimal result = rate.getRate().multiply(new BigDecimal(sum))
                .setScale(Currencies.DECIMAL_SCALE, RoundingMode.HALF_UP);
        BigDecimal resultInLoc = this.locConverter.convert(result, target);

        List<Hotel> hotels = null;
        List<Country> country = this.countryService.findAllByCurrency(target);
        if (country != null) {
            hotels = this.hotelService.findAllAvailableHotels(resultInLoc.doubleValue(), target);
            hotels.forEach(h -> h.setNights(resultInLoc.doubleValue()));
        }

        this.addAttributes(model, currencies, sourceCurrency, targetCurrency, sum, result
                , resultInLoc, top8Rates, hotels);
        return Templates.BASE;
    }

    private void addAttributes(Model model, List<Currency> currencies, String sourceCurrency, String targetCurrency
            , Object sum, BigDecimal rate, BigDecimal resultInLoc, List<List<Rate>> top8Rates, List<Hotel> hotels) {

        model.addAttribute(Placeholders.TOP_10_RATES, top8Rates);
        model.addAttribute(Placeholders.SOURCE_CURRENCY, sourceCurrency);
        model.addAttribute(Placeholders.TARGET_CURRENCY, targetCurrency);
        model.addAttribute(Placeholders.CURRENCIES, currencies);
        model.addAttribute(Placeholders.INPUT_SUM, sum);
        model.addAttribute(Placeholders.RESULT, rate);
        model.addAttribute(Placeholders.RESULT_LOC, resultInLoc);
        model.addAttribute(Placeholders.HOTELS, hotels);
        model.addAttribute(Placeholders.VIEW, Templates.INDEX);
    }

    private boolean sumIsValid(String sum) {
        try {
            return Double.parseDouble(sum) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
