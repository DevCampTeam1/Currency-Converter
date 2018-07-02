package com.devcamp.currencyconverter.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_code", unique = true)
    private String currencyCode;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "rate")
    private BigDecimal rate;

    public Currency() {
    }

    public Currency(String currencyCode, String currencyName, BigDecimal rate) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.rate = rate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return this.currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public BigDecimal getRate() {
        return this.rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
