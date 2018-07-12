package com.devcamp.currencyconverter.model.views;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HotelView {
    private String destination;

    private BigDecimal price;

    private String url;

    private String pictureUrl;

    private String totalNights;

    public HotelView() {
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

    public String getTotalNights() {
        return this.totalNights;
    }

    public void setNights(BigDecimal sum) {
        BigDecimal totalNights = sum.divide(this.price, RoundingMode.HALF_UP)
                .setScale(0, RoundingMode.DOWN);
        if (totalNights.compareTo(BigDecimal.ONE) == 0) {
            this.totalNights = " 1 night.";
        } else if (totalNights.compareTo(BigDecimal.valueOf(14)) <= 0) {
            this.totalNights = " " + String.valueOf(totalNights) + " nights.";
        } else {
            this.totalNights = " 14+ nights.";
        }
    }
}
