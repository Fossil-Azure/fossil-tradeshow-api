package com.fossil.tradeshow.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Product {

    @Id
    private String sku;

    private String productTitle;

    private String imageUrl;

    private String brand;

    private String season;

    private String platform;

    private String Category;

    private List<String> ean;

    private List<String> upc;

    private List<String> jan;

    private double mrpUsd;

    private double mrpInr;

    private double mrpSgd;

    private double mrpHkd;

    private double mrpJpy;

    private double mrpAud;

    private List<String> sizes;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public List<String> getEan() {
        return ean;
    }

    public void setEan(List<String> ean) {
        this.ean = ean;
    }

    public List<String> getUpc() {
        return upc;
    }

    public void setUpc(List<String> upc) {
        this.upc = upc;
    }

    public List<String> getJan() {
        return jan;
    }

    public void setJan(List<String> jan) {
        this.jan = jan;
    }

    public double getMrpUsd() {
        return mrpUsd;
    }

    public void setMrpUsd(double mrpUsd) {
        this.mrpUsd = mrpUsd;
    }

    public double getMrpInr() {
        return mrpInr;
    }

    public void setMrpInr(double mrpInr) {
        this.mrpInr = mrpInr;
    }

    public double getMrpSgd() {
        return mrpSgd;
    }

    public void setMrpSgd(double mrpSgd) {
        this.mrpSgd = mrpSgd;
    }

    public double getMrpHkd() {
        return mrpHkd;
    }

    public void setMrpHkd(double mrpHkd) {
        this.mrpHkd = mrpHkd;
    }

    public double getMrpJpy() {
        return mrpJpy;
    }

    public void setMrpJpy(double mrpJpy) {
        this.mrpJpy = mrpJpy;
    }

    public double getMrpAud() {
        return mrpAud;
    }

    public void setMrpAud(double mrpAud) {
        this.mrpAud = mrpAud;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
