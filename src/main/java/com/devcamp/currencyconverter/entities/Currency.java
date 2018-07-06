package com.devcamp.currencyconverter.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true)
    private String code;

    @OneToMany(mappedBy = "currency")
    private List<Country> countries;

    public Currency() {
        this.countries = new ArrayList<>();
    }

    public Currency(String code) {
        this.countries = new ArrayList<>();
        this.code = code;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
