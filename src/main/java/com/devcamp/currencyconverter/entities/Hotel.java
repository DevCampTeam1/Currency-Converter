package com.devcamp.currencyconverter.entities;

import javax.persistence.*;


@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "destination")
    private String destination;

    @Column(name = "price")
    private Double price;

    @Column(name = "url")
    private String url;

    @Column(name = "picture_url")
    private String pictureUrl;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @Transient
    private String totalNights;

    public Hotel() {
    }

    public Hotel(String destination, Double price, String url, String pictureUrl) {
        this.destination = destination;
        this.price = price;
        this.url = url;
        this.pictureUrl = pictureUrl;
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

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
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

    public String getTotalNights() {
        return this.totalNights;
    }

    public void setNights(Double sum) {
        int totalNights = (int) (sum / this.price);
        if (totalNights == 1) {
            this.totalNights = " 1 night.";
        } else if (totalNights <= 14) {
            this.totalNights = " " + String.valueOf(totalNights) + " nights.";
        } else {
            this.totalNights = " 14+ nights.";
        }
    }
}
