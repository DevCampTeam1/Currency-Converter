package com.devcamp.currencyconverter.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "destination")
    private String destination;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "url")
    private String url;

    @Column(name = "picture_url")
    private String pictureUrl;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    public Hotel() {
    }

    public Hotel(String destination, BigDecimal price, String url, String pictureUrl, Country country) {
        this.destination = destination;
        this.price = price;
        this.url = url;
        this.pictureUrl = pictureUrl;
        this.country = country;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPictureUrl() {
        return this.pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
