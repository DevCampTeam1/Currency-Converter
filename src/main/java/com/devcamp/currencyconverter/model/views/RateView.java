package com.devcamp.currencyconverter.model.views;

import com.devcamp.currencyconverter.model.entities.Currency;
import java.math.BigDecimal;

public class RateView {
    private Currency sourceCurrency;

    private Currency targetCurrency;

    private BigDecimal rate;

    private Boolean rateHasDropped;

    public RateView() {
    }

    public Currency getSourceCurrency() {
        return this.sourceCurrency;
    }

    public void setSourceCurrency(Currency sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public Currency getTargetCurrency() {
        return this.targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getRate() {
        return this.rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Boolean getRateHasDropped() {
        return this.rateHasDropped;
    }

    public void setRateHasDropped(Boolean rateHasDropped) {
        this.rateHasDropped = rateHasDropped;
    }
}
