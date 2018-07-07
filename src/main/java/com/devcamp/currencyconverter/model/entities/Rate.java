package com.devcamp.currencyconverter.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_currency_id", referencedColumnName = "id")
    private Currency sourceCurrency;

    @ManyToOne
    @JoinColumn(name = "target_currency_id", referencedColumnName = "id")
    private Currency targetCurrency;

    @Column(name = "rate", nullable = false)
    private BigDecimal rate;

    public Rate() {
    }

    public Rate(Currency currencyCode, Currency currencyName, BigDecimal rate) {
        this.sourceCurrency = currencyCode;
        this.targetCurrency = currencyName;
        this.rate = rate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getSourceCurrency() {
        return this.sourceCurrency;
    }

    public void setSourceCurrency(Currency sourceCurrencyCode) {
        this.sourceCurrency = sourceCurrencyCode;
    }

    public Currency getTargetCurrency() {
        return this.targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrencyCode) {
        this.targetCurrency = targetCurrencyCode;
    }

    public BigDecimal getRate() {
        return this.rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
