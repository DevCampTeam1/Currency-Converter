package com.devcamp.currencyconverter.model.views;

public class HotelView {
    private String destination;

    private Double price;

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

    public void setTotalNights(String totalNights) {
        this.totalNights = totalNights;
    }
}
