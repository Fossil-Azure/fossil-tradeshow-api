package com.fossil.tradeshow.model;

import org.springframework.data.annotation.Id;

public class Rating {

    @Id
    private String id;
    private String productSku;
    private int rating;
    private String userId;

    public Rating() {}

    public Rating(String productSku, int rating, String userId) {
        this.productSku = productSku;
        this.rating = rating;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
